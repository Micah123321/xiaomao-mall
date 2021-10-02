package com.xiaomao6.xiaomaoware.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @ClassName MergePurchaseVo
 * @Description 简介
 * @Author Micah
 * @Date 2021/9/27 22:33
 * @Version 1.0
 **/
@Data
@Accessors(chain = true)
public class MergePurchaseVo {
    /*
      purchaseId: 1, //整单id
      items:[1,2,3,4] //合并项集合
     */
    private Long purchaseId;
    private List<Long> items;
}
