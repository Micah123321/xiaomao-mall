package com.xiaomao6.xiaomaoproduct.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaomao6.xiaomaoproduct.entity.AttrAttrgroupRelationEntity;
import com.xiaomao6.xiaomaoproduct.vo.AttrGroupRelationVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 属性&属性分组关联
 *
 * @author micah
 * @email mxmicah@qq.com
 * @date 2021-09-15 19:16:10
 */
@Mapper
public interface AttrAttrgroupRelationDao extends BaseMapper<AttrAttrgroupRelationEntity> {

    void deleteByRelationVos(@Param("relationVos")List<AttrGroupRelationVo> relationVos);
}
