package com.xiaomao6.xiaomaocoupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaomao6.common.to.SkuReductionTo;
import com.xiaomao6.common.utils.PageUtils;
import com.xiaomao6.xiaomaocoupon.entity.SkuFullReductionEntity;

import java.util.Map;

/**
 * 商品满减信息
 *
 * @author micah
 * @email mxmicah@qq.com
 * @date 2021-09-15 23:00:41
 */
public interface SkuFullReductionService extends IService<SkuFullReductionEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveInfo(SkuReductionTo skuReductionTo);
}

