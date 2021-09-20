package com.xiaomao6.xiaomaoorder.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaomao6.common.utils.PageUtils;
import com.xiaomao6.xiaomaoorder.entity.OrderEntity;

import java.util.Map;

/**
 * 订单
 *
 * @author micah
 * @email mxmicah@qq.com
 * @date 2021-09-15 23:12:18
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

