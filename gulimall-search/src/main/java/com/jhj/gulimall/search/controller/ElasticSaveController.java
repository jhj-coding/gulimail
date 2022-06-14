package com.jhj.gulimall.search.controller;

import com.jhj.common.exception.BizCodeEnume;
import com.jhj.common.to.es.SkuEsModel;
import com.jhj.common.utils.R;
import com.jhj.gulimall.search.service.ProductSaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @Description ElasticSaveController
 * @Author jhj
 * @Date 2022/6/1410:22
 **/
@Slf4j
@RequestMapping("/search/save")
@RestController
public class ElasticSaveController {
    @Resource
    ProductSaveService productSaveService;

    @PostMapping("/product")
    public R productStatusUp(@RequestBody List<SkuEsModel> skuEsModels){
        boolean b=false;
        try {
            b=productSaveService.productStatusUp(skuEsModels);
        } catch (IOException e) {
            log.error("商品上架错误",e);
            return R.error(BizCodeEnume.PRODUCT_UP_EXCEPTION.getCode(), BizCodeEnume.PRODUCT_UP_EXCEPTION.getMsg());
        }
        if (!b)
            return R.ok();
        else
            return R.error(BizCodeEnume.PRODUCT_UP_EXCEPTION.getCode(), BizCodeEnume.PRODUCT_UP_EXCEPTION.getMsg());
    }
}
