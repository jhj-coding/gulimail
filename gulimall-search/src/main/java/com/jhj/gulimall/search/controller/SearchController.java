package com.jhj.gulimall.search.controller;

import com.jhj.gulimall.search.service.MallSearchService;
import com.jhj.gulimall.search.vo.SearchParam;
import com.jhj.gulimall.search.vo.SearchResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Description SearchController
 * @Author jhj
 * @Date 2022/6/3017:43
 **/
@Controller
public class SearchController {

    @Resource
    MallSearchService mallSearchService;

    @RequestMapping("/list.html")
    public String listPage(SearchParam searchParam, Model model, HttpServletRequest request){
        searchParam.set_queryString(request.getQueryString());
        SearchResult result=mallSearchService.search(searchParam);
        model.addAttribute("result",result);
        return "list";
    }
}
