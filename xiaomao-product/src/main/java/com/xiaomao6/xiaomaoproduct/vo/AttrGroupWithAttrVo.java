package com.xiaomao6.xiaomaoproduct.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.xiaomao6.xiaomaoproduct.entity.AttrEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @ClassName AttrGroupWithAttrVo
 * @Description 简介
 * @Author Micah
 * @Date 2021/9/25 0:28
 * @Version 1.0
 **/
@Data
@Accessors(chain = true)
public class AttrGroupWithAttrVo {
    private static final long serialVersionUID = 1L;

    /**
     * 分组id
     */
    @TableId
    private Long attrGroupId;
    /**
     * 组名
     */
    private String attrGroupName;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 描述
     */
    private String descript;
    /**
     * 组图标
     */
    private String icon;
    /**
     * 所属分类id
     */
    private Long catelogId;

    private List<AttrEntity> attrs;
}
