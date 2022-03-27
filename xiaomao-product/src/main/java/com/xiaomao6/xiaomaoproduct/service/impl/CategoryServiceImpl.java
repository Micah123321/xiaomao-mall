package com.xiaomao6.xiaomaoproduct.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaomao6.common.utils.PageUtils;
import com.xiaomao6.common.utils.Query;
import com.xiaomao6.xiaomaoproduct.dao.CategoryBrandRelationDao;
import com.xiaomao6.xiaomaoproduct.dao.CategoryDao;
import com.xiaomao6.xiaomaoproduct.entity.CategoryBrandRelationEntity;
import com.xiaomao6.xiaomaoproduct.entity.CategoryEntity;
import com.xiaomao6.xiaomaoproduct.service.CategoryService;
import com.xiaomao6.xiaomaoproduct.vo.Category2Vo;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    CategoryBrandRelationDao categoryBrandRelationDao;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    RedissonClient redisson;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> getTreeList() {
        List<CategoryEntity> all = baseMapper.selectList(null);
        List<CategoryEntity> collect = all.stream()
                .filter(e -> e.getParentCid() == 0)
                .sorted(Comparator.comparingInt(e -> (e.getSort() == null ? 0 : e.getSort())))
                .map(e -> e.setChildren(getChildrenByRoot(e, all)))
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    public void removeByIdList(List<Long> asList) {
        //TODO 校验是否在其他表引用了该分类 如果有,则删除失败

        baseMapper.deleteBatchIds(asList);
    }

    @Override
    public Long[] getCatelogPath(Long catId) {
        ArrayList<Long> list = new ArrayList<>();
        getCatePath(catId, list);
        Collections.reverse(list);
        return list.toArray(new Long[list.size()]);
    }

    //递归查询分类
    private void getCatePath(Long catId, ArrayList<Long> list) {
        list.add(catId);
        CategoryEntity byId = this.getById(catId);
        if (byId.getParentCid() != 0) {
            getCatePath(byId.getParentCid(), list);
        }
    }


    @Caching(evict = {
            @CacheEvict(value = "category", key = "'getLevel1Category'"),
            @CacheEvict(value = "category", key = "'getCategory2List'")
    })
    @Override
    public void updateDetail(CategoryEntity category) {
        //TODO 可能存在的其他表的冗余字段,待更新
        this.updateById(category);
        //顺便更新关联表
        categoryBrandRelationDao.update(
                new CategoryBrandRelationEntity().setCatelogName(category.getName()).setCatelogId(category.getCatId()),
                new QueryWrapper<CategoryBrandRelationEntity>().eq("catelog_id", category.getCatId())
        );
    }

    @Cacheable(value = {"category"}, key = "#root.methodName",sync = true)
    @Override
    public List<CategoryEntity> getLevel1Category() {//直接取父id==0的值 则为一级分类
        List<CategoryEntity> list = baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", 0));
        return list;
    }

    @Cacheable(value = {"category"}, key = "#root.methodName")
    @Override
    public Map<String, List<Category2Vo>> getCategory2List() {
        return getCategory2ListByDBWithCache();
    }

    public Map<String, List<Category2Vo>> getCategory2ListByDb() {
        Map<String, List<Category2Vo>> result;
        //先从redis中拿json,如果没有则从数据库中查询,然后存储到缓存中
        String categoryList = redisTemplate.opsForValue().get("categoryList");
        if (StringUtils.isEmpty(categoryList)) {
            //为空则查库
            result = getCategory2ListByDBWithRedisson();//在这里万一有100万条数据同时访问,就是要查100w次数据库,不合理,得加锁
        } else {
            System.out.println("我直接取到了redis中的缓存");
            //string不为空,转为map
            result = JSON.parseObject(categoryList, new TypeReference<Map<String, List<Category2Vo>>>() {
            });
        }
        return result;
    }



    public Map<String, List<Category2Vo>> getCategory2ListByDBWithRedis() {


        //这里添加redis分布式锁

        //先插入分布式,如果成功则进入,不成功则重试

        String uuid = UUID.randomUUID().toString();
        Map<String, List<Category2Vo>> map = null;
        Boolean lock = false;
        while (!lock) {//如果得不到锁则一直执行
            lock = redisTemplate.opsForValue().setIfAbsent("lock", uuid, 300, TimeUnit.SECONDS);
        }

        //此时拿到了锁,先判断存不存在缓存
        String categoryListData = redisTemplate.opsForValue().get("categoryList");
        if (!StringUtils.isEmpty(categoryListData)) {

            //存在则执行原子删除命令 并返回缓存
            String script = "if redis.call(\"get\",KEYS[1]) == ARGV[1] then\n" +
                    "    return redis.call(\"del\",KEYS[1])\n" +
                    "else\n" +
                    "    return 0\n" +
                    "end";
            redisTemplate.execute(new DefaultRedisScript<>(script, Long.class), Collections.singletonList("lock"), uuid);


            System.out.println("在锁中,我查到了大哥的缓存存在,直接返回");
            return JSON.parseObject(categoryListData,
                    new TypeReference<Map<String, List<Category2Vo>>>() {
                    });//存在直接返回
        }

        //不存在查库
        map = getCateData();

        //插入到缓存
        String s = JSON.toJSONString(map);
        redisTemplate.opsForValue().set("categoryList", s, 1, TimeUnit.DAYS);

        //执行原子删除命令
        String script = "if redis.call(\"get\",KEYS[1]) == ARGV[1] then\n" +
                "    return redis.call(\"del\",KEYS[1])\n" +
                "else\n" +
                "    return 0\n" +
                "end";

        redisTemplate.execute(new DefaultRedisScript<>(script, Long.class), Collections.singletonList("lock"), uuid);


        return map;


    }

    public Map<String, List<Category2Vo>> getCategory2ListByDBWithRedisson() {
        //redisson拿锁
        RLock lock = redisson.getLock("categoryList-lock");
        Map<String, List<Category2Vo>> map = null;
        //此时拿到了锁,先判断存不存在缓存

        lock.lock(10, TimeUnit.SECONDS);//上锁
        try {
            String categoryListData = redisTemplate.opsForValue().get("categoryList");
            if (!StringUtils.isEmpty(categoryListData)) {

                System.out.println("在锁中,我查到了大哥的缓存存在,直接返回");
                return JSON.parseObject(categoryListData,
                        new TypeReference<Map<String, List<Category2Vo>>>() {
                        });//存在直接返回
            }
            //不存在查库
            map = getCateData();
            //插入到缓存
            String s = JSON.toJSONString(map);
            redisTemplate.opsForValue().set("categoryList", s, 1, TimeUnit.DAYS);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();//解锁
        }

        return map;
    }

    public Map<String, List<Category2Vo>> getCategory2ListByDBWithCache() {
        //redisson拿锁
        RLock lock = redisson.getLock("categoryList-lock");
        Map<String, List<Category2Vo>> map = null;
        //此时拿到了锁,先判断存不存在缓存

        lock.lock(10, TimeUnit.SECONDS);//上锁
        try {
            //不存在查库
            map = getCateData();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();//解锁
        }

        return map;
    }


    private Map<String, List<Category2Vo>> getCateData() {
        System.out.println("我要查库了");
        //先获得所有一级分类 然后遍历获得下面的二级分类组 再遍历二级分类下获得三级分类组
        List<CategoryEntity> categoryList = this.baseMapper.selectList(null);//这里查询分类表所有数据
        List<CategoryEntity> lv1List = getParent_cid(0L, categoryList);
        //k为前面id,v为通过lv1遍历取得下面的lv2集合
        return lv1List.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), lv1 -> {
            List<Category2Vo> list = new ArrayList<>();
            //查询,并且如果不为空
            List<CategoryEntity> lv2List = getParent_cid(lv1.getCatId(), categoryList);
            if (lv2List != null) {
                lv2List.forEach(lv2 -> {
                    //新建2级分类vo
                    Category2Vo category2Vo = new Category2Vo(lv1.getCatId().toString(), null, lv2.getCatId().toString(), lv2.getName());
                    //根据2级分类查询3级
                    List<CategoryEntity> lv3list = getParent_cid(lv2.getCatId(), categoryList);
                    //如果不为空,则遍历为lv3集合,同时加入到lv2类中
                    if (lv3list != null) {
                        List<Category2Vo.Catalog3Vo> collect = lv3list.stream().map(lv3 -> new Category2Vo.Catalog3Vo(lv2.getCatId().toString(), lv3.getCatId().toString(), lv3.getName())).collect(Collectors.toList());
                        category2Vo.setCatalog3List(collect);
                    }
                    list.add(category2Vo);
                });
            }
            return list;
        }));
    }



    public Map<String, List<Category2Vo>> getCategory2ListByDB() {

        synchronized (this) {//加锁 查询 同时在查询前判断是否存在缓存
            String categoryListData = redisTemplate.opsForValue().get("categoryList");
            if (!StringUtils.isEmpty(categoryListData)) {
                System.out.println("在锁中,我查到了大哥的缓存存在,直接返回");
                return JSON.parseObject(categoryListData,
                        new TypeReference<Map<String, List<Category2Vo>>>() {
                        });//存在直接返回
            }


            //先获得所有一级分类 然后遍历获得下面的二级分类组 再遍历二级分类下获得三级分类组
            Map<String, List<Category2Vo>> map = getCateData();

            //插入到缓存
            String s = JSON.toJSONString(map);
            redisTemplate.opsForValue().set("categoryList", s, 1, TimeUnit.DAYS);

            return map;
        }


    }

    private List<CategoryEntity> getParent_cid(Long parentCid, List<CategoryEntity> list) {
//        return this.baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", lv1.getCatId()));
        List<CategoryEntity> collect = list.stream().filter(e -> e.getParentCid() == parentCid).collect(Collectors.toList());
        return collect;
    }


    private List<CategoryEntity> getChildrenByRoot(CategoryEntity root, List<CategoryEntity> all) {
        List<CategoryEntity> collect = all.stream()
                .filter(e -> e.getParentCid().equals(root.getCatId()))
                .sorted(Comparator.comparingInt(e -> (e.getSort() == null ? 0 : e.getSort())))
                .map(e -> e.setChildren(getChildrenByRoot(e, all)))
                .collect(Collectors.toList());
        return collect;
    }


}
