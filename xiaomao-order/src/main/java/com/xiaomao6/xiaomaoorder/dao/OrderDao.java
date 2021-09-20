package com.xiaomao6.xiaomaoorder.dao;

import com.xiaomao6.xiaomaoorder.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author micah
 * @email mxmicah@qq.com
 * @date 2021-09-15 23:12:18
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
