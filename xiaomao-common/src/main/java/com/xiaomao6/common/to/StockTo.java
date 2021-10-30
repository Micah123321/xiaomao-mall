package com.xiaomao6.common.to;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @ClassName StockVo
 * @Description 简介
 * @Author Micah
 * @Date 2021/10/15 1:07
 * @Version 1.0
 **/
@Data
@Accessors(chain = true)
public class StockTo {
    private Long skuId;
    private Boolean hasStock;
}
