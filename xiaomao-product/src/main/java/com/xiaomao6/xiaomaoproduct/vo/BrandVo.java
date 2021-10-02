package com.xiaomao6.xiaomaoproduct.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @ClassName BrandVo
 * @Description 简介
 * @Author Micah
 * @Date 2021/9/25 0:08
 * @Version 1.0
 **/
@Data
@Accessors(chain = true)
public class BrandVo {
//		"brandId": 0,
//      "brandName": "string",
    private Long brandId;
    private String brandName;
}
