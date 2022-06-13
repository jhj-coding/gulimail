package com.jhj.gulimall.product.dao;

import com.jhj.gulimall.product.entity.AttrEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品属性
 * 
 * @author jhj
 * @email jhj@gmail.com
 * @date 2022-05-14 10:29:24
 */
@Mapper
public interface AttrDao extends BaseMapper<AttrEntity> {

    List<Long> selectSearchAttrs(@Param("attrIds") List<Long> collect1);
}
