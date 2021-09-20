package com.xiaomao6.xiaomaoproduct.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaomao6.common.utils.PageUtils;
import com.xiaomao6.xiaomaoproduct.entity.SkuImagesEntity;

import java.util.Map;

/**
 * sku图片
 *
 * @author micah
 * @email mxmicah@qq.com
 * @date 2021-09-15 19:16:09
 */
public interface SkuImagesService extends IService<SkuImagesEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

