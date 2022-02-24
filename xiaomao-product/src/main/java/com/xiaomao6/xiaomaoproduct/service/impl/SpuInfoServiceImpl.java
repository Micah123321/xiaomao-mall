package com.xiaomao6.xiaomaoproduct.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaomao6.common.constant.ProductConstant;
import com.xiaomao6.common.to.SkuReductionTo;
import com.xiaomao6.common.to.SpuBoundsTo;
import com.xiaomao6.common.to.StockTo;
import com.xiaomao6.common.to.es.SkuEsModel;
import com.xiaomao6.common.utils.PageUtils;
import com.xiaomao6.common.utils.Query;
import com.xiaomao6.common.utils.R;
import com.xiaomao6.xiaomaoproduct.dao.SpuInfoDao;
import com.xiaomao6.xiaomaoproduct.dao.SpuInfoDescDao;
import com.xiaomao6.xiaomaoproduct.entity.*;
import com.xiaomao6.xiaomaoproduct.feign.SearchSkuFeign;
import com.xiaomao6.xiaomaoproduct.feign.SpuToCouponFeignService;
import com.xiaomao6.xiaomaoproduct.feign.WareFeignService;
import com.xiaomao6.xiaomaoproduct.service.*;
import com.xiaomao6.xiaomaoproduct.vo.*;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Resource
    BrandService brandService;

    @Resource
    SpuInfoDescDao spuInfoDescDao;

    @Resource
    SpuImagesService spuImagesService;

    @Resource
    CategoryService categoryService;

    @Resource
    ProductAttrValueService productAttrValueService;

    @Resource
    AttrService attrService;

    @Resource
    SkuInfoService skuInfoService;

    @Resource
    SkuImagesService skuImagesService;

    @Resource
    SkuSaleAttrValueService skuSaleAttrValueService;

    @Resource
    SpuToCouponFeignService couponFeignService;

    @Resource
    WareFeignService wareFeignService;

    @Resource
    SearchSkuFeign searchSkuFeign;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }


    @Override
    @Transactional
    public void saveSpuVo(SaveSpuVo saveSpuVo) {
//    spu 宏观的商品信息(类似与类)

//    sku 更加细节的商品信息(实体)

//    1. 首先保存spu的基本信息 `pms_spu_info`
        SpuInfoEntity spuInfoEntity = new SpuInfoEntity()
                .setUpdateTime(new Date())
                .setCreateTime(new Date());
        BeanUtils.copyProperties(saveSpuVo, spuInfoEntity);
        //此时spuInfoEntity对象取到spuid
        this.save(spuInfoEntity);
//    2. 保存spu的描述图片 `pms_spu_info_desc`
        List<String> decript = saveSpuVo.getDecript();
        SpuInfoDescEntity spuInfoDescEntity = new SpuInfoDescEntity()
                .setSpuId(spuInfoEntity.getId())
                .setDecript(String.join(",", decript));
        spuInfoDescDao.insert(spuInfoDescEntity);
//    3. 保存spu的图片集 `pms_spu_images`
        List<String> images = saveSpuVo.getImages();
        List<SpuImagesEntity> imgCollect = images.stream()
                .map(e -> new SpuImagesEntity()
                        .setSpuId(spuInfoEntity.getId())
                        .setImgUrl(e))
                .filter(e -> e.getImgUrl() != null)
                .filter(e -> !StringUtil.isEmpty(e.getImgUrl().trim()))
                .collect(Collectors.toList());
        spuImagesService.saveBatch(imgCollect);
//    4. 保存spu的规格参数 `pms_product_attr_value`
        List<BaseAttrs> baseAttrs = saveSpuVo.getBaseAttrs();
        List<ProductAttrValueEntity> baseAttrCollect = baseAttrs.stream().map(e ->
                new ProductAttrValueEntity()
                        .setSpuId(spuInfoEntity.getId())
                        .setAttrId(e.getAttrId())
                        .setAttrName(attrService.getById(e.getAttrId()).getAttrName())
                        .setAttrValue(e.getAttrValues())
                        .setQuickShow(e.getShowDesc())
        ).collect(Collectors.toList());
        productAttrValueService.saveBatch(baseAttrCollect);
//    5. 保存spu的积分信息(跨库) `xiaomaomall-sms` -> `sms_spu_bounds` `sms_sku_ladder`

        SpuBoundsTo spuBoundsTo = new SpuBoundsTo()
                .setSpuId(spuInfoEntity.getId())
                .setWork(0);
        BeanUtils.copyProperties(saveSpuVo.getBounds(), spuBoundsTo);
        if (spuBoundsTo.getBuyBounds().compareTo(new BigDecimal("0")) > 0 ||
                spuBoundsTo.getBuyBounds().compareTo(new BigDecimal("0")) > 0) {
            R save = couponFeignService.save(spuBoundsTo);
            if (!save.isSuccess()) {
                log.error("远程保存积分信息失败");
            }
        }


//    6. 保存当前spu对应的sku信息
//    6.1)sku的基本信息 `pms_sku_info`
        List<Skus> skus = saveSpuVo.getSkus();
        skus.forEach(item -> {
            List<Images> imagesList = item.getImages();
            SkuInfoEntity skuInfoEntity = new SkuInfoEntity()
                    .setBrandId(saveSpuVo.getBrandId())
                    .setCatalogId(saveSpuVo.getCatalogId())
                    .setSaleCount(0L)
                    .setSpuId(spuInfoEntity.getId())
                    .setSkuDefaultImg("");
            for (Images img : imagesList) {
                if (img.getDefaultImg() == 1)
                    skuInfoEntity.setSkuDefaultImg(img.getImgUrl());
            }
            BeanUtils.copyProperties(item, skuInfoEntity);

            skuInfoService.save(skuInfoEntity);
//    6.2)sku的图片信息 `pms_sku_images`
            List<SkuImagesEntity> skuImagesEntityList = imagesList.stream().map(e -> new SkuImagesEntity()
                    .setDefaultImg(e.getDefaultImg())
                    .setSkuId(skuInfoEntity.getSkuId())
                    .setImgUrl(e.getImgUrl()))
                    .filter(e -> e.getImgUrl() != null)
                    .filter(e -> !StringUtil.isEmpty(e.getImgUrl().trim()))
                    .collect(Collectors.toList());

            skuImagesService.saveBatch(skuImagesEntityList);
//    6.3)sku的销售属性信息 `pms_sku_sale_attr_value`
            List<Attr> attrs = item.getAttr();
            List<SkuSaleAttrValueEntity> saleAttrCollect = attrs.stream()
                    .map(e -> {
                        SkuSaleAttrValueEntity valueEntity = new SkuSaleAttrValueEntity()
                                .setSkuId(skuInfoEntity.getSkuId());
                        BeanUtils.copyProperties(e, valueEntity);
                        return valueEntity;
                    })
                    .collect(Collectors.toList());
            skuSaleAttrValueService.saveBatch(saleAttrCollect);

//    6.4)sku的优惠,满减信息(跨库)  `xiaomaomall-sms` -> `sms_sku_ladder` `sms_sku_full_reduction` `sms_member_price`
//                                                          满量优惠             满减                    会员价格
            SkuReductionTo skuReductionTo = new SkuReductionTo()
                    .setFullCount(item.getFullCount())
                    .setSkuId(spuInfoEntity.getId())
                    .setPriceStatus(item.getPriceStatus());
            BeanUtils.copyProperties(item, skuReductionTo);
            if (skuReductionTo.getFullPrice().compareTo(new BigDecimal("0")) > 0 ||
                    skuReductionTo.getReducePrice().compareTo(new BigDecimal("0")) > 0) {
                R r = couponFeignService.saveSkuReduction(skuReductionTo);
                if (!r.isSuccess()) {
                    log.error("远程保存优惠信息失败");
                }
            }

        });


    }

    /**
     * 待条件的spu查询
     *
     * @param params 条件
     * @return 分页
     */
    @Override
    public PageUtils queryPageByParams(Map<String, Object> params) {
        //创建查询语句
        QueryWrapper<SpuInfoEntity> queryWrapper = new QueryWrapper<>();

        //下面是各属性值的判断,可以参照api发送的参数对应操作

        String key = (String) params.get("key");
        if (!StringUtil.isEmpty(key)) {
            queryWrapper.and(e -> {
                e.eq("id", key).or().like("spu_name", key);
            });
        }
        String status = (String) params.get("status");
        if (!StringUtil.isEmpty(status) && StringDtZero(status)) {
            queryWrapper.eq("publish_status", status);
        }
        String catelogId = (String) params.get("catelogId");
        if (!StringUtil.isEmpty(catelogId) && StringDtZero(catelogId)) {
            queryWrapper.eq("catalog_id", catelogId);
        }
        String brandId = (String) params.get("brandId");
        if (!StringUtil.isEmpty(brandId) && StringDtZero(brandId)) {
            queryWrapper.eq("brand_id", brandId);
        }
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void up(Long spuId) {
        List<SkuEsModel> list;

        //查询skus列表
        List<SkuInfoEntity> skus = skuInfoService.getSkusBySpuId(spuId);

        //查询品牌信息
        BrandEntity brandEntity = brandService.getById(skus.get(0).getBrandId());

        //获取分类信息
        CategoryEntity categoryEntity = categoryService.getById(skus.get(0).getCatalogId());

        //获取对应attr,规格参数
        //首先获取spu对应的属性,提取处理对应id
        List<ProductAttrValueEntity> attrValueEntities = productAttrValueService.list(new QueryWrapper<ProductAttrValueEntity>().eq("spu_id", spuId));
        List<Long> ids = attrValueEntities.stream().map(ProductAttrValueEntity::getAttrId).collect(Collectors.toList());
        //根据对应id查询出来所有的属性原数据
        List<AttrEntity> attrList = attrService.selectByListId(ids);
        //过滤出来要检索的属性,进行属性拷贝
        List<SkuEsModel.Attr> attrCollect = attrList.stream().filter(e -> e.getSearchType() == 1).map(e -> {
            SkuEsModel.Attr attr = new SkuEsModel.Attr();
            BeanUtils.copyProperties(e, attr);
            return attr;
        }).collect(Collectors.toList());
        List<Long> skuIds = skus.stream().map(SkuInfoEntity::getSkuId).collect(Collectors.toList());
        //调用远程端口查询库存
        R r = null;
        try {
            r = wareFeignService.checkStock(skuIds);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<Long,Boolean> stockMap;
        //想要被转换的类型
        //因为是个内部保护类 所以需要new xxx(){
        // } 添加方法体
        TypeReference<List<StockTo>> typeReference = new TypeReference<List<StockTo>>(){};

        assert r != null;
        //这里语法糖,将list转换为map
        stockMap=r.getData(typeReference).stream().collect(Collectors.toMap(StockTo::getSkuId,StockTo::getHasStock));
        Map<Long, Boolean> finalStockMap = stockMap;
        list = skus.stream().map(e -> {
            SkuEsModel skuEsModel = new SkuEsModel();
            BeanUtils.copyProperties(e, skuEsModel);
            /**
             * 以下类型不同
             *             skuPrice;
             *             skuDefaultImg
             *             brandName;
             *             brandImg;
             *             catalogName;
             *             hasStock;
             *             hotScore;
             *             attrs;
             */
            skuEsModel.setSkuPrice(e.getPrice())
                    .setSkuImg(e.getSkuDefaultImg())
                    .setBrandImg(brandEntity.getLogo())
                    .setBrandName(brandEntity.getName())
                    .setCatalogName(categoryEntity.getName())
                    .setAttrs(attrCollect)//设置属性
            ;

            //设置当前库存 如果存在则设置对应的库存
            if(finalStockMap.containsKey(e.getSkuId()))
                skuEsModel.setHasStock(finalStockMap.get(e.getSkuId()));


            return skuEsModel;
        }).collect(Collectors.toList());

        //TODO 热度评分

        //发送到elasticsearch,通过feign调用search服务
        try {
            R r1 = searchSkuFeign.SkuSave(list);
            if (r1.isSuccess()) {
                //修改状态
                this.baseMapper.updateStatus(spuId, ProductConstant.status.UP.getCode());
            } else {
                //TODO 重调机制
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 判断一个字符串是否大于0
     *
     * @param str 字符串
     * @return 是否大于
     */
    private Boolean StringDtZero(String str) {
        return Integer.parseInt(str) > 0;
    }


}
