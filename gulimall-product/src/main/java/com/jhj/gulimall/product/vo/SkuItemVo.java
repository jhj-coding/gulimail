package com.jhj.gulimall.product.vo;

import com.jhj.gulimall.product.entity.SkuImagesEntity;
import com.jhj.gulimall.product.entity.SkuInfoEntity;
import com.jhj.gulimall.product.entity.SpuInfoDescEntity;
import lombok.Data;

import java.util.List;

@Data
public class SkuItemVo {

    SkuInfoEntity info;

    boolean hasStock=true;

    List<SkuImagesEntity> images;

    List<SkuItemSaleAttrVo> saleAttr;

    SpuInfoDescEntity desc;

    List<SkuItemAttrGroupVo> groupAttrs;


}
