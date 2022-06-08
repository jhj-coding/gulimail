package com.jhj.gulimall.product.fegin;

import com.jhj.common.to.SpuBoundTo;
import com.jhj.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author jhj
 * @data 2022/6/8 - 12:41
 */
@FeignClient("gulimall-coupon")
public interface CouponFeginService {
    @PostMapping("/coupon/spubounds/save")
    R saveSpuBounds(@RequestBody SpuBoundTo spuBoundTo);
}
