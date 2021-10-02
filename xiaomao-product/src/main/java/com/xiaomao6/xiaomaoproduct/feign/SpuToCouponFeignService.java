package com.xiaomao6.xiaomaoproduct.feign;

import com.xiaomao6.common.to.SkuReductionTo;
import com.xiaomao6.common.to.SpuBoundsTo;
import com.xiaomao6.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("xiaomao-coupon")
public interface SpuToCouponFeignService {
    /**
     * 保存
     */
    @PostMapping("xiaomaocoupon/spubounds/save")
    R save(@RequestBody SpuBoundsTo spuBounds);

    @PostMapping("xiaomaocoupon/skufullreduction/saveinfo")
    R saveSkuReduction(SkuReductionTo skuReductionTo);
}
