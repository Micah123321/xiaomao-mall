package com.xiaomao6.xiaomaocoupon.dao;

import com.xiaomao6.xiaomaocoupon.entity.CouponSpuRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券与产品关联
 * 
 * @author micah
 * @email mxmicah@qq.com
 * @date 2021-09-15 23:00:41
 */
@Mapper
public interface CouponSpuRelationDao extends BaseMapper<CouponSpuRelationEntity> {
	
}
