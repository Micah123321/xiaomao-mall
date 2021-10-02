package com.xiaomao6.xiaomaoproduct.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @ClassName AttrGroupRelationVo
 * @Description 简介
 * @Author Micah
 * @Date 2021/9/21 22:43
 * @Version 1.0
 **/
@Data
@Accessors(chain = true)
public class AttrGroupRelationVo {
    //[{"attrId":1,"attrGroupId":2}]
    private Long attrId;
    private Long attrGroupId;
}
