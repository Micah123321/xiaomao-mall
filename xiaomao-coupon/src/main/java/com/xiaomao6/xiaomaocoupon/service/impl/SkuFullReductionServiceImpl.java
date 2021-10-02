package com.xiaomao6.xiaomaocoupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaomao6.common.to.MemberPrice;
import com.xiaomao6.common.to.SkuReductionTo;
import com.xiaomao6.common.utils.PageUtils;
import com.xiaomao6.common.utils.Query;
import com.xiaomao6.xiaomaocoupon.dao.SkuFullReductionDao;
import com.xiaomao6.xiaomaocoupon.entity.MemberPriceEntity;
import com.xiaomao6.xiaomaocoupon.entity.SkuFullReductionEntity;
import com.xiaomao6.xiaomaocoupon.entity.SkuLadderEntity;
import com.xiaomao6.xiaomaocoupon.service.MemberPriceService;
import com.xiaomao6.xiaomaocoupon.service.SkuFullReductionService;
import com.xiaomao6.xiaomaocoupon.service.SkuLadderService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("skuFullReductionService")
public class SkuFullReductionServiceImpl extends ServiceImpl<SkuFullReductionDao, SkuFullReductionEntity> implements SkuFullReductionService {

    @Resource
    SkuLadderService skuLadderService;

    @Resource
    MemberPriceService memberPriceService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuFullReductionEntity> page = this.page(
                new Query<SkuFullReductionEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void saveInfo(SkuReductionTo skuReductionTo) {
//    6.4)sku的优惠,满减信息(跨库)  `xiaomaomall-sms` -> `sms_sku_ladder` `sms_sku_full_reduction` `sms_member_price`
//                                                          满量优惠             满减                    会员价格
        //sms_sku_ladder

        if (skuReductionTo.getFullPrice().compareTo(new BigDecimal("0")) > 0)//判断满减价格大于零
            skuLadderService.save(
                    new SkuLadderEntity()
                            .setSkuId(skuReductionTo.getSkuId())
                            .setAddOther(skuReductionTo.getPriceStatus())
                            .setFullCount(skuReductionTo.getFullCount())
                            .setDiscount(skuReductionTo.getDiscount()));

        //sms_sku_full_reduction

        SkuFullReductionEntity skuFullReductionEntity = new SkuFullReductionEntity()
                .setAddOther(skuReductionTo.getCountStatus());
        BeanUtils.copyProperties(skuReductionTo, skuFullReductionEntity);
        if (skuFullReductionEntity.getReducePrice().compareTo(new BigDecimal("0")) > 0)//判减价大于零
            this.save(skuFullReductionEntity);

        //sms_member_price
        List<MemberPrice> memberPrice = skuReductionTo.getMemberPrice();
        List<MemberPriceEntity> collect = memberPrice.stream().map(e -> {
            MemberPriceEntity memberPriceEntity = new MemberPriceEntity();
            memberPriceEntity
                    .setSkuId(skuReductionTo.getSkuId())
                    .setAddOther(1)
                    .setMemberLevelId(e.getId())
                    .setMemberPrice(e.getPrice())
                    .setMemberLevelName(e.getName());
            return memberPriceEntity;
        }).filter(item -> item.getMemberPrice().compareTo(new BigDecimal("0")) > 0)//过滤掉会员价格小于零的
                .collect(Collectors.toList());
        memberPriceService.saveBatch(collect);
    }

}
