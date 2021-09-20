package com.xiaomao6.xiaomaoproduct.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

/**
 * 品牌分类关联
 *
 * @author micah
 * @email mxmicah@qq.com
 * @date 2021-09-15 19:16:10
 */
@Accessors(chain = true)
@Getter
@Setter
@TableName("pms_category_brand_relation")
public class CategoryBrandRelationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Long id;
	/**
	 * 品牌id
	 */
	private Long brandId;
	/**
	 * 分类id
	 */
	private Long catelogId;
	/**
	 *
	 */
	private String brandName;
	/**
	 *
	 */
	private String catelogName;

}
