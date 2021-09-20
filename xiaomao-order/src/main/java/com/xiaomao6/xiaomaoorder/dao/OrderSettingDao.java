package com.xiaomao6.xiaomaoorder.dao;

import com.xiaomao6.xiaomaoorder.entity.OrderSettingEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单配置信息
 * 
 * @author micah
 * @email mxmicah@qq.com
 * @date 2021-09-15 23:12:17
 */
@Mapper
public interface OrderSettingDao extends BaseMapper<OrderSettingEntity> {
	
}
