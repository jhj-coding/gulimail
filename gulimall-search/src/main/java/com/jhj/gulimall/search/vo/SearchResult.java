package com.jhj.gulimall.search.vo;

import com.jhj.common.to.es.SkuEsModel;
import lombok.Data;

import java.util.List;

/**
 * @Description SearchResponse
 * @Author jhj
 * @Date 2022/7/19:43
 **/
@Data
public class SearchResult {
    private List<SkuEsModel> products;

    private Integer pageNum;

    private Long total;

    private Integer totalPages;

    private List<Integer> pageNavs;

    private List<BrandVo> brands;

    private List<AttrVo> attrs;

    private List<CatalogVo> catalogs;
    @Data
    public static class BrandVo{
        private Long brandId;
        private String brandName;
        private String brandImg;
    }


    @Data
    public static class CatalogVo{
        private Long catalogId;
        private String catalogName;
    }


    @Data
    public static class AttrVo{
        private Long attrId;
        private String attrName;
        private List<String> attrValue;
    }
}
