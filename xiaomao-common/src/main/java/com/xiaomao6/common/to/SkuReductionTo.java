package com.xiaomao6.common.to;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName SkuReductionTo
 * @Description 简介
 * @Author Micah
 * @Date 2021/9/26 18:30
 * @Version 1.0
 **/
@Getter
@Setter
@Accessors(chain = true)
public class SkuReductionTo {
    private Long skuId;
    private Integer fullCount;
    private BigDecimal discount;
    private Integer countStatus;
    private BigDecimal fullPrice;
    private BigDecimal reducePrice;
    private Integer priceStatus;
    private List<MemberPrice> memberPrice;
}
