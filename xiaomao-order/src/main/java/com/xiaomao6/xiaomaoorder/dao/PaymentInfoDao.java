package com.xiaomao6.xiaomaoorder.dao;

import com.xiaomao6.xiaomaoorder.entity.PaymentInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 支付信息表
 * 
 * @author micah
 * @email mxmicah@qq.com
 * @date 2021-09-15 23:12:17
 */
@Mapper
public interface PaymentInfoDao extends BaseMapper<PaymentInfoEntity> {
	
}
