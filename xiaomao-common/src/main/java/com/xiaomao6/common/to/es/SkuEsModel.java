package com.xiaomao6.common.to.es;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * 存放到es中的类型
 * @ClassName SkuEsModel
 * @Description 简介
 * @Author Micah
 * @Date 2021/10/14 8:53
 * @Version 1.0
 **/
@Data
@Accessors(chain = true)
public class SkuEsModel {
    private Long skuId;
    private Long spuId;
    private String skuTitle;
    private BigDecimal skuPrice;
    private String skuImg;
    private Long saleCount;
    private boolean hasStock;
    private Long hotScore;
    private Long brandId;
    private Long catalogId;
    private String brandName;
    private String brandImg;
    private String catalogName;
    private List<Attr> attrs;

    /**
     * 内部类
     */
    @Data
    public static class Attr{
        private Long attrId;
        private String attrName;
        private String attrValue;
    }

}
