package com.xiaomao6.xiaomaocoupon.dao;

import com.xiaomao6.xiaomaocoupon.entity.MemberPriceEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品会员价格
 * 
 * @author micah
 * @email mxmicah@qq.com
 * @date 2021-09-15 23:00:41
 */
@Mapper
public interface MemberPriceDao extends BaseMapper<MemberPriceEntity> {
	
}
