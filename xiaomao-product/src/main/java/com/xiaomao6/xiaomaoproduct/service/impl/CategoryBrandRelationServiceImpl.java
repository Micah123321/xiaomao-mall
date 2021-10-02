package com.xiaomao6.xiaomaoproduct.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaomao6.common.utils.PageUtils;
import com.xiaomao6.common.utils.Query;
import com.xiaomao6.xiaomaoproduct.dao.BrandDao;
import com.xiaomao6.xiaomaoproduct.dao.CategoryBrandRelationDao;
import com.xiaomao6.xiaomaoproduct.dao.CategoryDao;
import com.xiaomao6.xiaomaoproduct.entity.BrandEntity;
import com.xiaomao6.xiaomaoproduct.entity.CategoryBrandRelationEntity;
import com.xiaomao6.xiaomaoproduct.entity.CategoryEntity;
import com.xiaomao6.xiaomaoproduct.service.CategoryBrandRelationService;
import com.xiaomao6.xiaomaoproduct.vo.BrandVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {

    @Autowired
    BrandDao brandDao;

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    CategoryBrandRelationDao categoryBrandRelationDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    /**
     * 根据id查出对应name用于添加
     * @param brandId 品牌id
     * @param catelogId 分类id
     */
    @Override
    @Transactional
    public void saveByAllId(Long brandId, Long catelogId) {
        BrandEntity brandEntity = brandDao.selectById(brandId);
        String brandEntityName = brandEntity.getName();
        CategoryEntity categoryEntity = categoryDao.selectById(catelogId);
        String categoryEntityName = categoryEntity.getName();
        this.save(new CategoryBrandRelationEntity()
                .setBrandId(brandId)
                .setBrandName(brandEntityName)
                .setCatelogId(catelogId)
                .setCatelogName(categoryEntityName));
    }

    /**
     * 根据分类id查询关联的品牌列表
     * @param catId
     * @return
     */
    @Override
    public List<BrandVo> getBrandsByCatId(Long catId) {
        //首先查询在关联表的属性,所有分类id为xx的
        List<CategoryBrandRelationEntity> entityList = categoryBrandRelationDao.selectList(
                new QueryWrapper<CategoryBrandRelationEntity>().eq("catelog_id", catId));
        //之后收集为BrandVo
        List<BrandVo> collect = entityList.stream()
                .map(e -> new BrandVo().setBrandId(e.getBrandId()).setBrandName(e.getBrandName())).collect(Collectors.toList());
        return collect;
    }

}
