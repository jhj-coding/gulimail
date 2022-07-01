package com.jhj.gulimall.search.vo;

import lombok.Data;

import java.util.List;

/**
 * @Description SearchParam
 * @Author jhj
 * @Date 2022/7/19:15
 **/
@Data
public class SearchParam {

    private String keyword;

    private Long catalog3Id;

    private String sort;

    private Integer hasStock;

    private String skuPrice;

    private List<Long> brandId;

    private List<String> attrs;

    private Integer pageNum=1;
}
