package com.jhj.gulimall.order.dao;

import com.jhj.gulimall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author jhj
 * @email jhj@gmail.com
 * @date 2022-05-14 17:25:47
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
