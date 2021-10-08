package com.xiaomao6.xiaomaoproduct.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaomao6.common.utils.PageUtils;
import com.xiaomao6.xiaomaoproduct.entity.ProductAttrValueEntity;

import java.util.List;
import java.util.Map;

/**
 * spu属性值
 *
 * @author micah
 * @email mxmicah@qq.com
 * @date 2021-09-15 19:16:09
 */
public interface ProductAttrValueService extends IService<ProductAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void updateAttr(Long spuId, List<ProductAttrValueEntity> list);

}

