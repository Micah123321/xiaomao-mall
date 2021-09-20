package com.xiaomao6.xiaomaoorder.dao;

import com.xiaomao6.xiaomaoorder.entity.OrderOperateHistoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单操作历史记录
 * 
 * @author micah
 * @email mxmicah@qq.com
 * @date 2021-09-15 23:12:18
 */
@Mapper
public interface OrderOperateHistoryDao extends BaseMapper<OrderOperateHistoryEntity> {
	
}
