package com.xiaomao6.xiaomaoproduct.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.xiaomao6.xiaomaoproduct.entity.AttrEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @ClassName AttrVo
 * @Description 简介
 * @Author Micah
 * @Date 2021/9/21 17:22
 * @Version 1.0
 **/
@Data
@Accessors(chain = true)
public class AttrRespVo extends AttrEntity {

//			"catelogName": "手机/数码/手机", //所属分类名字
//                    "groupName": "主体", //所属分组名字

    private String catelogName;
    private String groupName;
    //		"attrGroupId": 1, //分组id
//		"catelogId": 225, //分类id
//		"catelogPath": [2, 34, 225] //分类完整路径
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long attrGroupId;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long[] catelogPath;


}
