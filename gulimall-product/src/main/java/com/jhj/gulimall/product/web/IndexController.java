package com.jhj.gulimall.product.web;

import com.jhj.gulimall.product.entity.CategoryEntity;
import com.jhj.gulimall.product.service.CategoryService;
import com.jhj.gulimall.product.vo.Catelog2Vo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Description IndexController
 * @Author jhj
 * @Date 2022/6/1416:42
 **/
@Controller
public class IndexController {
    @Resource
    CategoryService categoryService;
    @GetMapping({"/","/index.html"})
    public String indexPage(Model model){
        List<CategoryEntity> categoryEntityList=categoryService.getLevel1Categorys();
        model.addAttribute("categorys",categoryEntityList);
        return "index";
    }

    @ResponseBody
    @GetMapping("/index/catalog.json")
    public Map<String, List<Catelog2Vo>> getCatalogJson(){
        Map<String, List<Catelog2Vo>> map= categoryService.getCatalogJson();
        return map;
    }
}
