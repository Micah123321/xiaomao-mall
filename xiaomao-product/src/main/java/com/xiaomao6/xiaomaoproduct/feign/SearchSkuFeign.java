package com.xiaomao6.xiaomaoproduct.feign;

import com.xiaomao6.common.to.es.SkuEsModel;
import com.xiaomao6.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @ClassName SearchSkuFeign
 * @Description 简介
 * @Author Micah
 * @Date 2021/10/18 18:02
 * @Version 1.0
 **/
@FeignClient("xiaomao-search")
public interface SearchSkuFeign {
    @PostMapping("search/save/skuSave")
     R SkuSave(@RequestBody List<SkuEsModel> list);
}
