package com.xiaomao6.xiaomaomember.dao;

import com.xiaomao6.xiaomaomember.entity.IntegrationChangeHistoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 积分变化历史记录
 * 
 * @author micah
 * @email mxmicah@qq.com
 * @date 2021-09-15 23:06:59
 */
@Mapper
public interface IntegrationChangeHistoryDao extends BaseMapper<IntegrationChangeHistoryEntity> {
	
}
