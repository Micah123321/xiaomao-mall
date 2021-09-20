package com.xiaomao6.xiaomaocoupon.dao;

import com.xiaomao6.xiaomaocoupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author micah
 * @email mxmicah@qq.com
 * @date 2021-09-15 23:00:41
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
