package com.xiaomao6.xiaomaoware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaomao6.common.utils.PageUtils;
import com.xiaomao6.xiaomaoware.entity.WareSkuEntity;

import java.util.Map;

/**
 * 商品库存
 *
 * @author micah
 * @email mxmicah@qq.com
 * @date 2021-09-15 23:15:40
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

