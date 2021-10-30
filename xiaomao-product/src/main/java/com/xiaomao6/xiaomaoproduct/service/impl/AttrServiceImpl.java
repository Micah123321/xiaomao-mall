package com.xiaomao6.xiaomaoproduct.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaomao6.common.constant.AttrTypeEnum;
import com.xiaomao6.common.utils.PageUtils;
import com.xiaomao6.common.utils.Query;
import com.xiaomao6.xiaomaoproduct.dao.AttrAttrgroupRelationDao;
import com.xiaomao6.xiaomaoproduct.dao.AttrDao;
import com.xiaomao6.xiaomaoproduct.dao.AttrGroupDao;
import com.xiaomao6.xiaomaoproduct.dao.CategoryDao;
import com.xiaomao6.xiaomaoproduct.entity.AttrAttrgroupRelationEntity;
import com.xiaomao6.xiaomaoproduct.entity.AttrEntity;
import com.xiaomao6.xiaomaoproduct.entity.AttrGroupEntity;
import com.xiaomao6.xiaomaoproduct.entity.CategoryEntity;
import com.xiaomao6.xiaomaoproduct.service.AttrAttrgroupRelationService;
import com.xiaomao6.xiaomaoproduct.service.AttrService;
import com.xiaomao6.xiaomaoproduct.service.CategoryService;
import com.xiaomao6.xiaomaoproduct.vo.AttrRespVo;
import com.xiaomao6.xiaomaoproduct.vo.AttrVo;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {
    @Resource
    AttrAttrgroupRelationDao attrAttrgroupRelationDao;

    @Resource
    CategoryDao categoryDao;

    @Resource
    AttrGroupDao attrGroupDao;

    @Resource
    CategoryService categoryService;

    @Resource
    AttrAttrgroupRelationService attrAttrgroupRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    /**
     * service
     * 保存属性,顺带将属性提交附带的分组id添加到分组管理表
     *
     * @param attr 属性vo(携带分组id)
     */
    @Override
    @Transactional//多表操作都建议添加事务
    public void saveInGroup(AttrVo attr) {
        AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
        AttrEntity attrEntity = new AttrEntity();  //这里使用了复制字段的方法BeanUtils.copyProperties(要复制的,要被复制的)
        BeanUtils.copyProperties(attr, attrEntity);
        this.save(attrEntity);
        if (attr.getAttrType().equals(AttrTypeEnum.ATTR_TYPE_BASE.getCode())&& attr.getAttrGroupId()!=null)
            attrAttrgroupRelationDao.insert(  //最后插入到关联表
                    relationEntity
                            .setAttrGroupId(attr.getAttrGroupId())
                            .setAttrId(attrEntity.getAttrId()));
    }

    /**
     * 根据cateId查询对应的属性
     *
     * @param params    传入键值对
     * @param catelogId 分类id
     * @param attrType  参数类型 0销售 1基本
     * @return 分页数据
     */
    @Override
    @Transactional
    public PageUtils queryPageByCatelog(Map<String, Object> params, Long catelogId, String attrType) {
        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<AttrEntity>().eq("attr_type", "base".equalsIgnoreCase(attrType) ? 1 : 0);//实现定义一个空查询
        String key = (String) params.get("key");//提取key
        if (catelogId != 0) {//判断如果有id则根据id查
            wrapper.eq("catelog_id", catelogId);
        }
        if (!StringUtil.isEmpty(key)) {//key不为空则模糊查询
            wrapper.and(e -> e.eq("attr_id", key).or().like("attr_name", key));
        }
        IPage<AttrEntity> page = this.page(//创建查询语句,返回一个IPage对象
                new Query<AttrEntity>().getPage(params),
                wrapper
        );

        List<Object> collect = page.getRecords().stream().map(e -> {//提取IPage中的list做流循环
            AttrRespVo attrRespVo = new AttrRespVo();//生成一个返回vo
            CategoryEntity categoryEntity = categoryDao.selectById(e.getCatelogId());//首先查询分类的name
            AttrAttrgroupRelationEntity relationEntity = attrAttrgroupRelationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>()
                    .eq("attr_id", e.getAttrId()));//通过属性id查关联表查到关联的属性分组id
            if (relationEntity != null && "base".equalsIgnoreCase(attrType)) {
                AttrGroupEntity groupEntity = attrGroupDao.selectById(relationEntity.getAttrGroupId());
                if (groupEntity != null) {
                    attrRespVo.setGroupName(groupEntity.getAttrGroupName());//设置对应的分类名称和分组名称
                }
            }
            //根据id查询分组的名称
            BeanUtils.copyProperties(e, attrRespVo);//将AttrEntity赋予到attrRespVo
            attrRespVo.setCatelogName(categoryEntity.getName());//设置对应的分类名称和分组名称

            return attrRespVo;//返回,替换list
        }).collect(Collectors.toList());

        PageUtils pageUtils = new PageUtils(page);

        pageUtils.setList(collect);//在这里进行替换

        return pageUtils;
    }

    /**
     * 细节查询单体
     *
     * @param attrId 属性id
     * @return 返回实体, 内含CatelogPath和AttrGroupId
     */
    @Override
    @Transactional
    public AttrEntity getDetailById(Long attrId) {
        AttrEntity byId = this.getById(attrId);//查询到属性实体
        AttrRespVo attrRespVo = new AttrRespVo();//新建一个属性返回实体
        BeanUtils.copyProperties(byId, attrRespVo);//将属性实体赋值到返回实体
        //查询关联表,看是否存在关联组id
        AttrAttrgroupRelationEntity relationEntity = attrAttrgroupRelationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrId));
        if (relationEntity != null && byId.getAttrType().equals(AttrTypeEnum.ATTR_TYPE_BASE.getCode())) {
            //如果存在则设置上去
            attrRespVo.setAttrGroupId(relationEntity.getAttrGroupId());
        }
        //调用原来的查询分类路径方法,返回路径
        attrRespVo.setCatelogPath(categoryService.getCatelogPath(byId.getCatelogId()));
        return attrRespVo;
    }

    /**
     * 更新属性数据以及关联组
     *
     * @param attr 属性
     */
    @Override
    @Transactional
    public void updateDetail(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);
        this.update(attrEntity, new QueryWrapper<AttrEntity>().eq("attr_id", attr.getAttrId()));//先将基础的属性更新了
        QueryWrapper<AttrAttrgroupRelationEntity> wrapper = new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attr.getAttrId());
        AttrAttrgroupRelationEntity relationEntity = attrAttrgroupRelationDao.selectOne(wrapper);//查询关联表
        if (attr.getAttrGroupId() != null && attr.getAttrType().equals(AttrTypeEnum.ATTR_TYPE_BASE.getCode())) {
            if (relationEntity != null)//如果关联表不为空,则更新
                attrAttrgroupRelationDao.update(new AttrAttrgroupRelationEntity().setAttrGroupId(attr.getAttrGroupId()),
                        wrapper);
            else//如果为空,则添加新数据
                attrAttrgroupRelationDao.insert(new AttrAttrgroupRelationEntity()
                        .setAttrGroupId(attr.getAttrGroupId())
                        .setAttrId(attr.getAttrId()));

        }

    }

    /**
     * 获取可以关联的属性列表
     * @param params 参数
     * @param attrgroupId 分组id
     * @return 分页对象
     */
    @Override
    public PageUtils getNoRelationPage(Map<String, Object> params, Long attrgroupId) {
        //获取没有关联当前属性分组的属性列表

        AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrgroupId);

        //首先获取当前分组的属性值
        Long catelogId = attrGroupEntity.getCatelogId();

        //然后获取当前分类为当前属性值的列表
        List<AttrAttrgroupRelationEntity> groupIds = attrAttrgroupRelationService.list(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", attrGroupEntity.getAttrGroupId()));

        List<Long> attrIds = groupIds.stream().map(AttrAttrgroupRelationEntity::getAttrId).collect(Collectors.toList());

        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<AttrEntity>().eq("catelog_id", catelogId)
                .eq("attr_type",AttrTypeEnum.ATTR_TYPE_BASE.getCode());

        if (groupIds!=null&&groupIds.size()>0){//如果不为空,则排除
            wrapper.notIn("attr_id", attrIds);
        }

        //获取关联表中其他已关联属性用于排除
        List<AttrAttrgroupRelationEntity> noGroupIds = attrAttrgroupRelationService.list(new QueryWrapper<AttrAttrgroupRelationEntity>().ne("attr_group_id", attrGroupEntity.getAttrGroupId()));

        List<Long> noAttrIds = noGroupIds.stream().map(AttrAttrgroupRelationEntity::getAttrId).collect(Collectors.toList());

        if (noGroupIds!=null&&noGroupIds.size()>0){//如果不为空,则排除
            wrapper.notIn("attr_id", noAttrIds);
        }

        //最后判断key用于模糊搜索
        String key= (String) params.get("key");
        if (!StringUtil.isEmpty(key))
            wrapper.and(e->e.eq("attr_id",key).or().like("attr_name",key));

        //使用this.page获取分页对象
        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params), wrapper);

        return new PageUtils(page);
    }

    @Override
    public List<AttrEntity> selectByListId(List<Long> ids) {
        return baseMapper.selectByListId(ids);
    }

}
