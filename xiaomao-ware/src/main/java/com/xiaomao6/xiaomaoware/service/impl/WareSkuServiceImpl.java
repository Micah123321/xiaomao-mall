package com.xiaomao6.xiaomaoware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaomao6.common.utils.PageUtils;
import com.xiaomao6.common.utils.Query;
import com.xiaomao6.xiaomaoware.dao.WareSkuDao;
import com.xiaomao6.xiaomaoware.entity.WareSkuEntity;
import com.xiaomao6.xiaomaoware.service.WareSkuService;
import com.xiaomao6.common.to.StockTo;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<WareSkuEntity> queryWrapper = new QueryWrapper<>();

        /**
         * 查询分页 判断条件
         * skuId:
         * wareId:
         */

        String skuId = (String) params.get("skuId");
        if (!StringUtil.isEmpty(skuId)) {
            queryWrapper.eq("sku_id", skuId);
        }

        String wareId = (String) params.get("wareId");
        if (!StringUtil.isEmpty(wareId)) {
            queryWrapper.eq("ware_id", wareId);
        }

        IPage<WareSkuEntity> page = this.page(
                new Query<WareSkuEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    /**
     * 查询库存
     * @param ids id集合
     * @return 库存集合
     */
    @Override
    public List<StockTo> selectStock(List<Long> ids) {
        return ids.stream()
                .map(e -> {
                    Integer hasStock = baseMapper.getHasStock(e);
                    StockTo stockTo = new StockTo().setSkuId(e);
                    //如果库存大于0 则设置有库存
                    if (hasStock != null && hasStock > 0) {
                        stockTo.setHasStock(true);
                    }
                    return stockTo;
                })
                .collect(Collectors.toList());
    }

}
