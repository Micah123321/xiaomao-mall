package com.xiaomao6.xiaomaoproduct.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaomao6.common.utils.PageUtils;
import com.xiaomao6.xiaomaoproduct.entity.CategoryBrandRelationEntity;
import com.xiaomao6.xiaomaoproduct.vo.BrandVo;

import java.util.List;
import java.util.Map;

/**
 * 品牌分类关联
 *
 * @author micah
 * @email mxmicah@qq.com
 * @date 2021-09-15 19:16:10
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveByAllId(Long brandId, Long catelogId);

    List<BrandVo> getBrandsByCatId(Long catId);
}

