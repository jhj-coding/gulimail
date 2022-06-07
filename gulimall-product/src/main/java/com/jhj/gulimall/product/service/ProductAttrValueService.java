package com.jhj.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jhj.common.utils.PageUtils;
import com.jhj.gulimall.product.entity.ProductAttrValueEntity;

import java.util.List;
import java.util.Map;

/**
 * spu属性值
 *
 * @author jhj
 * @email jhj@gmail.com
 * @date 2022-05-14 10:29:23
 */
public interface ProductAttrValueService extends IService<ProductAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveProductAttr(List<ProductAttrValueEntity> collect);
}

