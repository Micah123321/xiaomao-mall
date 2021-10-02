package com.xiaomao6.xiaomaoproduct.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaomao6.common.utils.PageUtils;
import com.xiaomao6.xiaomaoproduct.entity.SkuInfoEntity;

import java.util.Map;

/**
 * sku信息
 *
 * @author micah
 * @email mxmicah@qq.com
 * @date 2021-09-15 19:16:10
 */
public interface SkuInfoService extends IService<SkuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageByParams(Map<String, Object> params);
}

