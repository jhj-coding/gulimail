package com.jhj.common.to.es;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description SkuEsModel
 * @Author jhj
 * @Date 2022/6/1315:43
 **/
@Data
public class SkuEsModel {
    private Long skuId;
    private Long spuId;
    private String skuTitle;
    private BigDecimal skuPrice;
    private String skuImg;
    private Long saleCount;
    private Boolean hasStock;
    private Long hotScore;
    private Long brandId;
    private Long catalogId;
    private String brandName;
    private String brandImg;
    private String catalogName;
    private List<Attrs> attrs;
    public static class Attrs{
        private Long attrId;
        private String attrName;
        private String attrValue;
    }
}
