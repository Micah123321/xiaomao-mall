package com.xiaomao6.xiaomaoorder.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaomao6.common.utils.PageUtils;
import com.xiaomao6.xiaomaoorder.entity.OrderItemEntity;

import java.util.Map;

/**
 * 订单项信息
 *
 * @author micah
 * @email mxmicah@qq.com
 * @date 2021-09-15 23:12:18
 */
public interface OrderItemService extends IService<OrderItemEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

