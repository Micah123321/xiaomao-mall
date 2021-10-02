package com.xiaomao6.xiaomaoware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaomao6.common.utils.PageUtils;
import com.xiaomao6.common.utils.Query;
import com.xiaomao6.xiaomaoware.dao.PurchaseDetailDao;
import com.xiaomao6.xiaomaoware.entity.PurchaseDetailEntity;
import com.xiaomao6.xiaomaoware.service.PurchaseDetailService;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("purchaseDetailService")
public class PurchaseDetailServiceImpl extends ServiceImpl<PurchaseDetailDao, PurchaseDetailEntity> implements PurchaseDetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<PurchaseDetailEntity> queryWrapper = new QueryWrapper<>();
        /**
         * key:
         * status:
         * wareId:
         */

        String key = (String) params.get("key");
        if (!StringUtil.isEmpty(key)) {
            queryWrapper.and(e -> {
                e.eq("purchase_id", key).or().like("sku_id", key);
            });
        }

        String status = (String) params.get("status");
        if (!StringUtil.isEmpty(status)) {
            queryWrapper.eq("status", status);
        }

        String wareId = (String) params.get("wareId");
        if (!StringUtil.isEmpty(wareId)) {
            queryWrapper.eq("ware_id", wareId);
        }

        IPage<PurchaseDetailEntity> page = this.page(
                new Query<PurchaseDetailEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

}
