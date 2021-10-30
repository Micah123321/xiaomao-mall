package com.xiaomao6.search.controller;

import com.xiaomao6.common.exception.BizCodeEnum;
import com.xiaomao6.common.to.es.SkuEsModel;
import com.xiaomao6.common.utils.R;
import com.xiaomao6.search.service.SaveSkuService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName SaveSkuController
 * @Description 简介
 * @Author Micah
 * @Date 2021/10/18 17:20
 * @Version 1.0
 **/
@RestController
@RequestMapping("search/save")
public class SaveSkuController {
    @Resource
    SaveSkuService saveSkuService;

    @PostMapping("/skuSave")
    public R SkuSave(@RequestBody List<SkuEsModel> list){
        Boolean b= null;
        try {
            b = saveSkuService.saveSkus(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.check(!b, BizCodeEnum.PRODUCT_UP_EXCEPTION.getCode(),BizCodeEnum.PRODUCT_UP_EXCEPTION.getMsg());
    }
}
