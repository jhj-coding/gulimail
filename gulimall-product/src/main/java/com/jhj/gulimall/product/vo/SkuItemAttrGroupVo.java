package com.jhj.gulimall.product.vo;

import lombok.Data;

import java.util.List;

@Data
public class SkuItemAttrGroupVo{
    private String groupName;
    private List<Attr> attrs;
}