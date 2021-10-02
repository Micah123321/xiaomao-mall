package com.xiaomao6.common.to;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @ClassName SpuBoundsTo
 * @Description 简介
 * @Author Micah
 * @Date 2021/9/25 19:01
 * @Version 1.0
 **/
@Data
@Accessors(chain = true)
public class SpuBoundsTo {

    private BigDecimal buyBounds;

    private BigDecimal growBounds;

    private Long spuId;
    /**
     * 优惠生效情况[1111（四个状态位，从右到左）;0 - 无优惠，成长积分是否赠送;1 - 无优惠，购物积分是否赠送;2 - 有优惠，成长积分是否赠送;3 - 有优惠，购物积分是否赠送【状态位0：不赠送，1：赠送】]
     */
    private Integer work;
}
