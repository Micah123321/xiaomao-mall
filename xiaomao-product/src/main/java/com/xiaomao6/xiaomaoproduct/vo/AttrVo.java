package com.xiaomao6.xiaomaoproduct.vo;

import com.xiaomao6.xiaomaoproduct.entity.AttrEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @ClassName AttrVo
 * @Description 简介
 * @Author Micah
 * @Date 2021/9/21 17:22
 * @Version 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class AttrVo extends AttrEntity {

    /**
     * 属性分组id
     */
    private Long attrGroupId;

}
