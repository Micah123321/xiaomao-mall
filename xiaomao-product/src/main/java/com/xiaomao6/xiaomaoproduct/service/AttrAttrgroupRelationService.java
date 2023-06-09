package com.xiaomao6.xiaomaoproduct.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaomao6.common.utils.PageUtils;
import com.xiaomao6.xiaomaoproduct.entity.AttrAttrgroupRelationEntity;
import com.xiaomao6.xiaomaoproduct.vo.AttrGroupRelationVo;

import java.util.Map;

/**
 * 属性&属性分组关联
 *
 * @author micah
 * @email mxmicah@qq.com
 * @date 2021-09-15 19:16:10
 */
public interface AttrAttrgroupRelationService extends IService<AttrAttrgroupRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void deleteByRelationVos(AttrGroupRelationVo[] relationVos);

    void relation(AttrGroupRelationVo[] relationVo);
}

