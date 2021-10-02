package com.xiaomao6.xiaomaoproduct.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaomao6.common.utils.PageUtils;
import com.xiaomao6.xiaomaoproduct.entity.AttrEntity;
import com.xiaomao6.xiaomaoproduct.entity.AttrGroupEntity;
import com.xiaomao6.xiaomaoproduct.vo.AttrGroupWithAttrVo;

import java.util.List;
import java.util.Map;

/**
 * 属性分组
 *
 * @author micah
 * @email mxmicah@qq.com
 * @date 2021-09-15 19:16:10
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageByCateId(Map<String, Object> params, Long catelogId);

    List<AttrEntity> getAttrListByGroupId(Long attrgroupId);


    List<AttrGroupWithAttrVo> getGroupWithAttrByCatelogId(Long catelogId);
}

