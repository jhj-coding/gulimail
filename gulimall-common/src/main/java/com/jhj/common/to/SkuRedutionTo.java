package com.jhj.common.to;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description SkuRedutionTo
 * @Author jhj
 * @Date 2022/6/814:12
 **/
@Data
public class SkuRedutionTo {
    private Long skuId;
    private int fullCount;
    private BigDecimal discount;
    private int countStatus;
    private BigDecimal fullPrice;
    private BigDecimal reducePrice;
    private int priceStatus;
    private List<MemberPrice> memberPrice;
}
