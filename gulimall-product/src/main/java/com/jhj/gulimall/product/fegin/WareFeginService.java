package com.jhj.gulimall.product.fegin;

import com.jhj.common.utils.R;
import com.jhj.gulimall.product.vo.SkuHasStockVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("gulimall-ware")
public interface WareFeginService {
    @PostMapping("/ware/waresku/hasstock")
    //@RequiresPermissions("ware:waresku:delete")
    public R getSkusHasStock(@RequestBody List<Long> skuIds);
}
