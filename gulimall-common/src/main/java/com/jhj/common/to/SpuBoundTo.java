package com.jhj.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author jhj
 * @data 2022/6/8 - 12:49
 */
@Data
public class SpuBoundTo {

    private Long spuId;
    private BigDecimal buyBounds;
    private BigDecimal growBounds;
}
