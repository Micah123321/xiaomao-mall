package com.xiaomao6.xiaomaoorder.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaomao6.common.utils.PageUtils;
import com.xiaomao6.xiaomaoorder.entity.RefundInfoEntity;

import java.util.Map;

/**
 * 退款信息
 *
 * @author micah
 * @email mxmicah@qq.com
 * @date 2021-09-15 23:12:18
 */
public interface RefundInfoService extends IService<RefundInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

