package com.xiaomao6.xiaomaoware.feign;

import com.xiaomao6.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName ProductFeignService
 * @Description 简介
 * @Author Micah
 * @Date 2021/10/4 21:57
 * @Version 1.0
 **/
//这里可以设置网关,然后通过带api的路径访问对应微服务
@FeignClient("xiaomao-product")

public interface ProductFeignService {
    /**
     * 信息
     */
    @RequestMapping("xiaomaoproduct/skuinfo/info/{id}")
     R info(@PathVariable("id") Long id);
}
