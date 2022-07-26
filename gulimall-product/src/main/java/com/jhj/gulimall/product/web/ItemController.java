package com.jhj.gulimall.product.web;

import com.jhj.gulimall.product.service.SkuInfoService;
import com.jhj.gulimall.product.vo.SkuItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ItemController {

    @Autowired
    SkuInfoService skuInfoService;

    @GetMapping("{skuId}.html")
    public String skuItem(@PathVariable("skuId") Long skuId, Model model){
        SkuItemVo vo=skuInfoService.item(skuId);
        model.addAttribute("item",vo);
        return "item";
    }
}
