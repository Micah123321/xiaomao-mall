package com.xiaomao6.xiaomaoproduct.feign;

import com.xiaomao6.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @ClassName WareFeignService
 * @Description 简介
 * @Author Micah
 * @Date 2021/10/15 1:19
 * @Version 1.0
 **/
@FeignClient("xiaomao-ware")
public interface WareFeignService {
    @PostMapping("xiaomaoware/waresku/stock")
    R checkStock(@RequestBody List<Long> ids);
}
