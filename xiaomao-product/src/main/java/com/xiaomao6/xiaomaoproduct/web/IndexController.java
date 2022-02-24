package com.xiaomao6.xiaomaoproduct.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @ClassName IndexController
 * @Description 简介
 * @Author Micah
 * @Date 2021/11/8 14:44
 * @Version 1.0
 **/
@Controller
public class IndexController {
    @GetMapping({"/","/index","/index.html"})
    public String indexPage(){

        return "index";
    }
}
