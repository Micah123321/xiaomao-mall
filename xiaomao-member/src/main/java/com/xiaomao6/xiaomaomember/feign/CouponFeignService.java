package com.xiaomao6.xiaomaomember.feign;

import com.xiaomao6.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("xiaomao-coupon")
public interface CouponFeignService {
    /**
     * 得到一个虚假的优惠券
     * @return 虚假的优惠券
     */
    @RequestMapping("xiaomaocoupon/coupon/test")
     R test();
}
