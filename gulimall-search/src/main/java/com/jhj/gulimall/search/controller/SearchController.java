package com.jhj.gulimall.search.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description SearchController
 * @Author jhj
 * @Date 2022/6/3017:43
 **/
@Controller
public class SearchController {

    @RequestMapping("/list.html")
    public String listPage(){
        return "list";
    }
}
