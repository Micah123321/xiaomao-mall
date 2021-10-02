package com.xiaomao6.xiaomaoware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaomao6.common.utils.PageUtils;
import com.xiaomao6.xiaomaoware.entity.PurchaseEntity;
import com.xiaomao6.xiaomaoware.vo.MergePurchaseVo;

import java.util.List;
import java.util.Map;

/**
 * 采购信息
 *
 * @author micah
 * @email mxmicah@qq.com
 * @date 2021-09-15 23:15:41
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageUnReceive(Map<String, Object> params);

    void mergePurchase(MergePurchaseVo mergePurchaseVo);

    void received(List<Long> ids);
}

