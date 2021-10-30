package com.xiaomao6.xiaomaoware.dao;

import com.xiaomao6.xiaomaoware.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品库存
 *
 * @author micah
 * @email mxmicah@qq.com
 * @date 2021-09-15 23:15:40
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {

    Integer getHasStock(Long e);
}
