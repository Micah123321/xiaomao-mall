package com.xiaomao6.search.vo;

import lombok.Data;

import java.util.List;

/**
 * @ClassName SearchParam
 * @Description 简介
 * @Author Micah
 * @Date 2022/3/26 17:02
 * @Version 1.0
 **/
@Data
public class SearchParam {

    //搜索词 v
    private String keyword;

    //三级分类id v
    private Long catalog3Id;
    //排序 v
    private String sort;
    //是否有货 v
    private Integer hasStock;
    //当前页码 v
    private Integer pageNum;
    //价格区间 v
    private String skuPrice;
    //品牌id(集合 v
    private List<Long> brandId;
    //属性(集合 v
    private List<String> attrs;

}
