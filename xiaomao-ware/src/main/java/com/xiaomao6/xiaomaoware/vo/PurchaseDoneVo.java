package com.xiaomao6.xiaomaoware.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @ClassName PurchaseDoneVo
 * @Description 简介
 * @Author Micah
 * @Date 2021/10/4 21:34
 * @Version 1.0
 **/
@Data
@Accessors(chain = true)
public class PurchaseDoneVo {
    @NotNull
    private Long id;
    @NotNull
    private List<PurchaseDoneItemVo> items;
}


