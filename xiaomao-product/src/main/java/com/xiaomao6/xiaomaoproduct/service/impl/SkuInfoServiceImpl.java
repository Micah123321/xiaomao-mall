package com.xiaomao6.xiaomaoproduct.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaomao6.common.utils.PageUtils;
import com.xiaomao6.common.utils.Query;
import com.xiaomao6.xiaomaoproduct.dao.SkuInfoDao;
import com.xiaomao6.xiaomaoproduct.entity.SkuInfoEntity;
import com.xiaomao6.xiaomaoproduct.service.SkuInfoService;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                new QueryWrapper<SkuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageByParams(Map<String, Object> params) {
        /**
         * key:
         * catelogId: 225
         * brandId: 1
         * min: 0
         * max: 0
         */
        QueryWrapper<SkuInfoEntity> queryWrapper = new QueryWrapper<>();

        String key = (String) params.get("key");
        if (!StringUtil.isEmpty(key)) {
            queryWrapper.and(e -> {
                e.eq("sku_id", key).or().like("sku_name", key);
            });
        }

        String catelogId = (String) params.get("catelogId");
        if (!StringUtil.isEmpty(catelogId) && StringDtZero(catelogId)) {
            queryWrapper.eq("catalog_id", catelogId);
        }

        String brandId = (String) params.get("brandId");
        if (!StringUtil.isEmpty(brandId) && StringDtZero(brandId)) {
            queryWrapper.eq("brand_id", brandId);
        }

        String min = (String) params.get("min");

        if (!StringUtil.isEmpty(min)){
            queryWrapper.ge("price", min);
        }

        String max = (String) params.get("max");
        if (!StringUtil.isEmpty(max) && StringDtZero(max)){
            queryWrapper.le("price", max);
        }

        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),queryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public List<SkuInfoEntity> getSkusBySpuId(Long spuId) {

        return this.list(new QueryWrapper<SkuInfoEntity>().eq("spu_id", spuId));
    }

    /**
     * 判断一个字符串是否大于0
     * @param str 字符串
     * @return 是否大于
     */
    private Boolean StringDtZero(String str) {
        //这里判断是否带了小数,如果有,则转换为BigDecimal类型进行计算
        if (str.contains(".")){
            boolean flag = false;
            try {
                 flag= new BigDecimal(str).compareTo(new BigDecimal("0")) > 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return flag;
        }
        return Integer.parseInt(str) > 0;
    }
}
