package com.jhj.gulimall.member.fegin;

import com.jhj.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author jhj
 * @data 2022/5/15 - 18:59
 */
//标明远程客户端 注解 参数为被调用方的在注册中心中配置的服务名
@FeignClient("gulimall-coupon")
@Service
public interface CouponFenginService {
    @RequestMapping("/coupon/coupon/member/list")
    public R membercoupons();
}
