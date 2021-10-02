package com.xiaomao6.xiaomaoproduct.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaomao6.common.utils.PageUtils;
import com.xiaomao6.common.utils.Query;
import com.xiaomao6.xiaomaoproduct.dao.BrandDao;
import com.xiaomao6.xiaomaoproduct.dao.CategoryBrandRelationDao;
import com.xiaomao6.xiaomaoproduct.entity.BrandEntity;
import com.xiaomao6.xiaomaoproduct.entity.CategoryBrandRelationEntity;
import com.xiaomao6.xiaomaoproduct.service.BrandService;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {

    @Autowired
    CategoryBrandRelationDao categoryBrandRelationDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BrandEntity> page = this.page(
                new Query<BrandEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils getListByKey(Map<String, Object> params) {
        String key = (String) params.get("key");
        QueryWrapper<BrandEntity> w = new QueryWrapper<>();
        if (!StringUtil.isEmpty(key)){
            w.eq("brand_id",key).or().like("name",key);
        }
        IPage<BrandEntity> page = this.page(
                new Query<BrandEntity>().getPage(params),
                w
        );
        return new PageUtils(page);
    }

    @Override
    public void updateDetail(BrandEntity brand) {
        this.updateById(brand);
        categoryBrandRelationDao.update(
                new CategoryBrandRelationEntity().setBrandName(brand.getName()).setBrandId(brand.getBrandId()),
                new QueryWrapper<CategoryBrandRelationEntity>().eq("brand_id",brand.getBrandId())
        );
        //TODO 可能存在的其他表的冗余字段,待更新
    }

}
