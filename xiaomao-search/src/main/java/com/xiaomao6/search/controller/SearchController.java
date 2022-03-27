package com.xiaomao6.search.controller;

import com.xiaomao6.search.service.SearchService;
import com.xiaomao6.search.vo.SearchParam;
import com.xiaomao6.search.vo.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName SearchController
 * @Description 简介
 * @Author Micah
 * @Date 2022/3/26 16:16
 * @Version 1.0
 **/
@Controller
public class SearchController {

    @Autowired
    SearchService searchService;

    @RequestMapping({"list.html","search.html","/"})
    public String list(SearchParam param, Model model){
        SearchResult result=searchService.search(param);
        model.addAttribute("result",result);
        return "list";
    }
}
