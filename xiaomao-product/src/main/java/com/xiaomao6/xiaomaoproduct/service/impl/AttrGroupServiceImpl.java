package com.xiaomao6.xiaomaoproduct.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaomao6.common.utils.PageUtils;
import com.xiaomao6.common.utils.Query;
import com.xiaomao6.xiaomaoproduct.dao.AttrDao;
import com.xiaomao6.xiaomaoproduct.dao.AttrGroupDao;
import com.xiaomao6.xiaomaoproduct.entity.AttrAttrgroupRelationEntity;
import com.xiaomao6.xiaomaoproduct.entity.AttrEntity;
import com.xiaomao6.xiaomaoproduct.entity.AttrGroupEntity;
import com.xiaomao6.xiaomaoproduct.service.AttrAttrgroupRelationService;
import com.xiaomao6.xiaomaoproduct.service.AttrGroupService;
import com.xiaomao6.xiaomaoproduct.service.AttrService;
import com.xiaomao6.xiaomaoproduct.vo.AttrGroupWithAttrVo;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Resource
    AttrDao attrDao;

    @Resource
    AttrAttrgroupRelationService attrAttrgroupRelationService;

    @Resource
    AttrService attrService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageByCateId(Map<String, Object> params, Long catelogId) {
        String key = (String) params.get("key");
        if (catelogId == 0) {
            QueryWrapper<AttrGroupEntity> wrapper = new QueryWrapper<>();
            if (!StringUtil.isEmpty(key)) {
                wrapper.and(e -> {
                    e.eq("attr_group_id", key).or().like("attr_group_name", key);
                });
            }
            IPage<AttrGroupEntity> page = this.page(
                    new Query<AttrGroupEntity>().getPage(params),
                    wrapper
            );
            return new PageUtils(page);
        } else {

            QueryWrapper<AttrGroupEntity> wrapper = new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId);
            if (!StringUtil.isEmpty(key)) {
                wrapper.and(e -> {
                    e.eq("attr_group_id", key).or().like("attr_group_name", key);
                });
            }
            IPage<AttrGroupEntity> page = this.page(
                    new Query<AttrGroupEntity>().getPage(params),
                    wrapper
            );
            return new PageUtils(page);
        }
    }

    /**
     * 通过属性分组id拿到属性列表
     * @param attrgroupId 属性分组id
     * @return 属性列表
     */
    @Override
    public List<AttrEntity> getAttrListByGroupId(Long attrgroupId) {
        //通过attr_group_id属性获取AttrAttrgroupRelationEntity数组
        List<AttrAttrgroupRelationEntity> entities = attrAttrgroupRelationService.list(
                new QueryWrapper<AttrAttrgroupRelationEntity>()
                        .eq("attr_group_id", attrgroupId));
        List<Long> ids = entities.stream()//通过方法引用的方式将所有AttrId拿到
                .map(AttrAttrgroupRelationEntity::getAttrId)
                .collect(Collectors.toList());
        List<AttrEntity> attrEntities=new ArrayList<>();
        if (ids.size()>0){//判断如果拿到的id数大于0,则解析循环查询
            attrEntities = attrDao.selectBatchIds(ids);
        }
        return attrEntities;
    }

    /**
     * 传入分类id查出对应的属性分组和旗下属性
     * @param catelogId
     * @return
     */
    @Override
    public List<AttrGroupWithAttrVo> getGroupWithAttrByCatelogId(Long catelogId) {
        //首先根据分类id查出对应的分组id 在group表中
        List<AttrGroupEntity> groupEntityList = this.list(new QueryWrapper<AttrGroupEntity>()
                .eq("catelog_id", catelogId));
        return groupEntityList.stream().map(e -> {
            //创建一个vo

            AttrGroupWithAttrVo attrGroupWithAttrVo = new AttrGroupWithAttrVo();
            //复制属性
            BeanUtils.copyProperties(e, attrGroupWithAttrVo);
            //根据属性分组id查关联表取出所对应属性集合
            List<AttrAttrgroupRelationEntity> attrList = attrAttrgroupRelationService.list(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", e.getAttrGroupId()));
            //将id提取出来
            List<Long> attrIds = attrList.stream().map(AttrAttrgroupRelationEntity::getAttrId).collect(Collectors.toList());
            //根据id查出对应的属性
            if (attrIds!=null&&attrIds.size()>0){
            List<AttrEntity> attr_id = attrService.list(new QueryWrapper<AttrEntity>().in("attr_id", attrIds));
            //设置到vo中
            attrGroupWithAttrVo.setAttrs(attr_id);
            }
            return attrGroupWithAttrVo;
            //收集一下
        }).collect(Collectors.toList());
    }


}
