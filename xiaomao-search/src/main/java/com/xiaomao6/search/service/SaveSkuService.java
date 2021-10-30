package com.xiaomao6.search.service;

import com.xiaomao6.common.to.es.SkuEsModel;

import java.io.IOException;
import java.util.List;

/**
 * @ClassName SaveSkuService
 * @Description 简介
 * @Author Micah
 * @Date 2021/10/18 17:23
 * @Version 1.0
 **/

public interface SaveSkuService {
    /**
     * 保存sku到es
     * @param list 要保存的数据集合
     */
    Boolean saveSkus(List<SkuEsModel> list) throws IOException;
}
