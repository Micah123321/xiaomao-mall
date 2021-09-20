package com.xiaomao6.xiaomaomember.dao;

import com.xiaomao6.xiaomaomember.entity.MemberLoginLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员登录记录
 * 
 * @author micah
 * @email mxmicah@qq.com
 * @date 2021-09-15 23:06:59
 */
@Mapper
public interface MemberLoginLogDao extends BaseMapper<MemberLoginLogEntity> {
	
}
