package com.xiaomao6.xiaomaoproduct.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaomao6.common.valid.ListValue;
import com.xiaomao6.common.valid.SaveGroup;
import com.xiaomao6.common.valid.UpdateGroup;
import com.xiaomao6.common.valid.UpdateStatusGroup;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * 品牌
 *
 * @author micah
 * @email mxmicah@qq.com
 * @date 2021-09-15 19:16:10
 */
@Accessors(chain = true)
@Getter
@Setter
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 品牌id
     */
    @TableId
    @NotNull(groups = {UpdateGroup.class, UpdateStatusGroup.class})
    @Null(groups = {SaveGroup.class})
    private Long brandId;
    /**
     * 品牌名
     */
    @NotEmpty(message = "品牌名称不能为空",groups = {SaveGroup.class,UpdateGroup.class})
    private String name;
    /**
     * 品牌logo地址
     */
    @URL(message = "logo必须是一个url地址",groups = {SaveGroup.class,UpdateGroup.class})
    @NotEmpty(message = "logo不能为空",groups = {SaveGroup.class,UpdateGroup.class})
    private String logo;
    /**
     * 介绍
     */
    private String descript;
    /**
     * 显示状态[0-不显示；1-显示]
     */
    @NotNull(message = "showStatus不能为空",groups = {SaveGroup.class,UpdateGroup.class, UpdateStatusGroup.class})
    @ListValue(vals = {0,1},groups = {SaveGroup.class,UpdateGroup.class, UpdateStatusGroup.class})
    private Integer showStatus;
    /**
     * 检索首字母
     */
    @Pattern(regexp = "^[a-zA-Z]$", message = "首字母得为一个字母",groups = {SaveGroup.class,UpdateGroup.class})
    @NotEmpty(message = "firstLetter不能为空",groups = {SaveGroup.class,UpdateGroup.class})
    private String firstLetter;
    /**
     * 排序
     */
    @NotNull(message = "sort不能为空",groups = {SaveGroup.class,UpdateGroup.class})
    @Min(value = 0, message = "sort不能小于0",groups = {SaveGroup.class,UpdateGroup.class})
    private Integer sort;

}
