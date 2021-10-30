package com.xiaomao6.xiaomaoproduct.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaomao6.common.utils.PageUtils;
import com.xiaomao6.xiaomaoproduct.entity.SpuInfoEntity;
import com.xiaomao6.xiaomaoproduct.vo.SaveSpuVo;

import java.util.Map;

/**
 * spu信息
 *
 * @author micah
 * @email mxmicah@qq.com
 * @date 2021-09-15 19:16:09
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSpuVo(SaveSpuVo saveSpuVo);

    PageUtils queryPageByParams(Map<String, Object> params);

    /**
     * 上架商品
     * @param spuId spuid
     */
    void up(Long spuId);
}

