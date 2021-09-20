package com.xiaomao6.xiaomaoware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaomao6.common.utils.PageUtils;
import com.xiaomao6.xiaomaoware.entity.WareInfoEntity;

import java.util.Map;

/**
 * 仓库信息
 *
 * @author micah
 * @email mxmicah@qq.com
 * @date 2021-09-15 23:15:41
 */
public interface WareInfoService extends IService<WareInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

