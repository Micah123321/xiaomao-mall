package com.xiaomao6.xiaomaocoupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaomao6.common.utils.PageUtils;
import com.xiaomao6.xiaomaocoupon.entity.CouponEntity;

import java.util.Map;

/**
 * 优惠券信息
 *
 * @author micah
 * @email mxmicah@qq.com
 * @date 2021-09-15 23:00:41
 */
public interface CouponService extends IService<CouponEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

