package com.xiaomao6.xiaomaoproduct.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaomao6.common.utils.PageUtils;
import com.xiaomao6.xiaomaoproduct.entity.AttrEntity;
import com.xiaomao6.xiaomaoproduct.vo.AttrVo;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author micah
 * @email mxmicah@qq.com
 * @date 2021-09-15 19:16:10
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveInGroup(AttrVo attr);

    PageUtils queryPageByCatelog(Map<String, Object> params, Long catelogId, String attrType);

    AttrEntity getDetailById(Long attrId);

    void updateDetail(AttrVo attr);

    PageUtils getNoRelationPage(Map<String, Object> params, Long attrgroupId);

    List<AttrEntity> selectByListId(List<Long> ids);
}

