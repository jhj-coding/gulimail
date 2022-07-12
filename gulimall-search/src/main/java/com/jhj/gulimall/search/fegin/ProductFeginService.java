package com.jhj.gulimall.search.fegin;

import com.jhj.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("gulimall-product")
public interface ProductFeginService {

    @GetMapping("/product/attr/info/{attrId}")
    //@RequiresPermissions("product:attr:info")
    public R attrinfo(@PathVariable("attrId") Long attrId);

    @GetMapping("/product/brand/infos")
    //@RequiresPermissions("product:brand:info")
    public R brandInfo(@RequestParam List<Long> brandIds);
}


