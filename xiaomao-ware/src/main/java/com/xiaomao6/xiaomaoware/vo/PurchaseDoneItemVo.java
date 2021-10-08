package com.xiaomao6.xiaomaoware.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @ClassName PurchaseDoneItemVo
 * @Description 简介
 * @Author Micah
 * @Date 2021/10/4 21:40
 * @Version 1.0
 **/
@Data
@Accessors(chain = true)
public
class PurchaseDoneItemVo {
    @NotNull
    private Long itemId;
    @NotNull
    private Integer status;
    private String reason;
}
