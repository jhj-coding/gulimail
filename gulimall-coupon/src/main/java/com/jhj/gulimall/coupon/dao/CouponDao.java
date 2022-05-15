package com.jhj.gulimall.coupon.dao;

import com.jhj.gulimall.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author jhj
 * @email jhj@gmail.com
 * @date 2022-05-14 17:07:20
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
