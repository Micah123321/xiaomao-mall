package com.xiaomao6.xiaomaoproduct.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaomao6.common.utils.PageUtils;
import com.xiaomao6.common.utils.Query;
import com.xiaomao6.xiaomaoproduct.dao.AttrAttrgroupRelationDao;
import com.xiaomao6.xiaomaoproduct.entity.AttrAttrgroupRelationEntity;
import com.xiaomao6.xiaomaoproduct.service.AttrAttrgroupRelationService;
import com.xiaomao6.xiaomaoproduct.vo.AttrGroupRelationVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("attrAttrgroupRelationService")
public class AttrAttrgroupRelationServiceImpl extends ServiceImpl<AttrAttrgroupRelationDao, AttrAttrgroupRelationEntity> implements AttrAttrgroupRelationService {

    @Resource
    AttrAttrgroupRelationDao attrAttrgroupRelationDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrAttrgroupRelationEntity> page = this.page(
                new Query<AttrAttrgroupRelationEntity>().getPage(params),
                new QueryWrapper<AttrAttrgroupRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void deleteByRelationVos(AttrGroupRelationVo[] relationVos) {
        List<AttrGroupRelationVo> list = Arrays.asList(relationVos);
        attrAttrgroupRelationDao.deleteByRelationVos(list);
    }

    /**
     * 添加方法
     * @param relationVo 关系处理vo数组
     */
    @Override
    public void relation(AttrGroupRelationVo[] relationVo) {
        //将数组流处理为list<AttrAttrgroupRelationEntity>集合
        List<AttrAttrgroupRelationEntity> list= Arrays.stream(relationVo).map(e->{
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(e,relationEntity);
            return relationEntity;
        }).collect(Collectors.toList());
        //再添加
        if (list.size()>0)
        this.saveBatch(list);
    }

}
