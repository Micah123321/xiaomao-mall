package com.xiaomao6.search.vo;

import com.xiaomao6.common.to.es.SkuEsModel;
import lombok.Data;

import java.util.List;

/**
 * @ClassName SearchResult
 * @Description 简介
 * @Author Micah
 * @Date 2022/3/27 18:26
 * @Version 1.0
 **/
@Data
public class SearchResult {
    //商品列表
    private List<SkuEsModel> products;

    /**
     * 当前页码
     */
    private Integer pageNum;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 总页码
     */
    private Integer totalPages;

    /**
     * 当前查询到的结果，所有涉及到的品牌
     */
    private List<BrandVo> brands;

    /**
     * 当前查询到的结果，所有涉及到的所有属性
     */
    private List<AttrVo> attrs;

    /**
     * 当前查询到的结果，所有涉及到的所有分类
     */
    private List<CatalogVo> catalogs;

    @Data
    public static class BrandVo {
        private Long brandId;
        private String brandName;
        private String brandImg;
    }


    @Data
    public static class AttrVo {
        private Long attrId;
        private String attrName;
        private List<String> attrValue;
    }


    @Data
    public static class CatalogVo {
        private Long catalogId;
        private String catalogName;
    }
}
