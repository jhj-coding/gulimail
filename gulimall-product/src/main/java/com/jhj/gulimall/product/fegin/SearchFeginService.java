package com.jhj.gulimall.product.fegin;

import com.jhj.common.to.es.SkuEsModel;
import com.jhj.common.utils.R;
import com.jhj.gulimall.product.fegin.fallback.SearchFeginServiceFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

//标明那个熔断处理
@FeignClient(value = "gulimall-search",fallback = SearchFeginServiceFallBack.class)
public interface SearchFeginService {
    @PostMapping("/search/save/product")
    public R productStatusUp(@RequestBody List<SkuEsModel> skuEsModels);
}
