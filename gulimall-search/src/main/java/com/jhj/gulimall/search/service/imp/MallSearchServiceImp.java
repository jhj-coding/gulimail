package com.jhj.gulimall.search.service.imp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.jhj.common.to.es.SkuEsModel;
import com.jhj.common.utils.R;
import com.jhj.gulimall.search.config.GulimallElasticSearchConfig;
import com.jhj.gulimall.search.constant.EsConstant;
import com.jhj.gulimall.search.fegin.ProductFeginService;
import com.jhj.gulimall.search.service.MallSearchService;
import com.jhj.gulimall.search.vo.AttrResponseVo;
import com.jhj.gulimall.search.vo.BrandVo;
import com.jhj.gulimall.search.vo.SearchParam;
import com.jhj.gulimall.search.vo.SearchResult;
import com.sun.xml.internal.ws.api.policy.SourceModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.search.join.ScoreMode;
import org.apache.tomcat.util.buf.UEncoder;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.nested.ParsedNested;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.util.UriEncoder;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MallSearchServiceImp implements MallSearchService {
    @Resource
    RestHighLevelClient client;
    @Resource
    ProductFeginService productFeginService;
    @Override
    public SearchResult search(SearchParam searchParam) {
        SearchResult result=null;

        SearchRequest searchRequest= buildSearchRequrest(searchParam);

        try {
            SearchResponse response = client.search(searchRequest, GulimallElasticSearchConfig.COMMON_OPTIONS);
            result=buildSearchResult(response,searchParam);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return result;
    }

    private SearchResult buildSearchResult(SearchResponse response,SearchParam param) {
        SearchResult result=new SearchResult();

        SearchHits hits = response.getHits();
        List<SkuEsModel> esModels=new ArrayList<>();
        if (hits.getHits()!=null && hits.getHits().length>0) {
            for (SearchHit hit : hits.getHits()) {
                String sourceAsString = hit.getSourceAsString();
                SkuEsModel skuEsModel1 = JSON.parseObject(sourceAsString, SkuEsModel.class);
                if (!StringUtils.isEmpty(param.getKeyword())) {
                    HighlightField skuTitle = hit.getHighlightFields().get("skuTitle");
                    String string = skuTitle.getFragments()[0].string();
                    skuEsModel1.setSkuTitle(string);
                }
                esModels.add(skuEsModel1);
            }
        }
        result.setProducts(esModels);


        List<SearchResult.AttrVo> attrVos=new ArrayList<>();
        ParsedNested attr_agg = response.getAggregations().get("attr_agg");
        ParsedLongTerms attr_id_agg = attr_agg.getAggregations().get("attr_id_agg");
        for (Terms.Bucket bucket : attr_id_agg.getBuckets()) {
            SearchResult.AttrVo attrVo = new SearchResult.AttrVo();
            long l = bucket.getKeyAsNumber().longValue();
            attrVo.setAttrId(l);
            String attr_name_agg = ((ParsedStringTerms) bucket.getAggregations().get("attr_name_agg")).getBuckets().get(0).getKeyAsString();
            attrVo.setAttrName(attr_name_agg);
            List<String> attr_value_agg = ((ParsedStringTerms) bucket.getAggregations().get("attr_value_agg")).getBuckets().stream().map(item -> {
                return ((Terms.Bucket) item).getKeyAsString();
            }).collect(Collectors.toList());
            attrVo.setAttrValue(attr_value_agg);

            result.getAttrIds().add(l);

            attrVos.add(attrVo);
        }

        result.setAttrs(attrVos);


        List<SearchResult.BrandVo> brandVos=new ArrayList<>();
        ParsedLongTerms brand_agg = response.getAggregations().get("brand_agg");
        for (Terms.Bucket bucket : brand_agg.getBuckets()) {
            SearchResult.BrandVo brandVo = new SearchResult.BrandVo();

            long l = bucket.getKeyAsNumber().longValue();
            brandVo.setBrandId(l);

            String brand_img_agg = ((ParsedStringTerms) bucket.getAggregations().get("brand_img_agg")).getBuckets().get(0).getKeyAsString();
            brandVo.setBrandImg(brand_img_agg);


            String brand_name_agg = ((ParsedStringTerms) bucket.getAggregations().get("brand_name_agg")).getBuckets().get(0).getKeyAsString();
            brandVo.setBrandName(brand_name_agg);

            brandVos.add(brandVo);
        }
        result.setBrands(brandVos);
        ParsedLongTerms catalog_agg = response.getAggregations().get("catalog_agg");
        List<SearchResult.CatalogVo> catalogVos=new ArrayList<>();
        List<? extends Terms.Bucket> buckets = catalog_agg.getBuckets();
        for (Terms.Bucket bucket : buckets) {
            SearchResult.CatalogVo catalogVo = new SearchResult.CatalogVo();
            String keyAsString = bucket.getKeyAsString();
            catalogVo.setCatalogId(Long.parseLong(keyAsString));

            ParsedStringTerms catalog_name_agg = bucket.getAggregations().get("catalog_name_agg");
            String keyAsString1 = catalog_name_agg.getBuckets().get(0).getKeyAsString();
            catalogVo.setCatalogName(keyAsString1);
            catalogVos.add(catalogVo);
        }
        result.setCatalogs(catalogVos);





        result.setPageNum(param.getPageNum());

        result.setTotal(hits.getTotalHits().value);

        int totalPages = (int) hits.getTotalHits().value % EsConstant.PRODUCT_PAGESIZE == 0 ? (int) hits.getTotalHits().value / EsConstant.PRODUCT_PAGESIZE : (int) hits.getTotalHits().value % (EsConstant.PRODUCT_PAGESIZE) + 1;
        result.setTotalPages(totalPages);
        ArrayList<Integer> integers = new ArrayList<>();
        for (int i=1;i<=totalPages;i++){
            integers.add(i);
        }
        result.setPageNavs(integers);

        if (param.getAttrs()!=null&&param.getAttrs().size()>0) {
            List<SearchResult.NavVo> collect = param.getAttrs().stream().map(attr -> {
                SearchResult.NavVo navVo = new SearchResult.NavVo();
                String[] s = attr.split("_");
                navVo.setNavValue(s[1]);

                R attrinfo = productFeginService.attrinfo(Long.parseLong(s[0]));
                result.getAttrIds().add(Long.valueOf(s[0]));
                if (attrinfo.getCode() == 0) {
                    AttrResponseVo attr1 = attrinfo.getData("attr", new TypeReference<AttrResponseVo>() {
                    });
                    navVo.setNavName(attr1.getAttrName());
                } else {
                    navVo.setNavName(s[0]);
                }
                String replace = replaceQueryString(param,attr,"attrs");
                navVo.setLink("http://search.gulimall.com/list.html?" + replace);
                return navVo;
            }).collect(Collectors.toList());
            result.setNavs(collect);
        }
        if(param.getBrandId()!=null &&  param.getBrandId().size()>0){
            List<SearchResult.NavVo> navs = result.getNavs();

            SearchResult.NavVo navVo = new SearchResult.NavVo();
            navVo.setNavName("品牌");
            R r = productFeginService.brandInfo(param.getBrandId());
            if (r.getCode()==0){
                List<BrandVo> data = r.getData("", new TypeReference<List<BrandVo>>() {
                });
                StringBuffer buffer=new StringBuffer();
                String replace="";
                for (BrandVo datum : data) {
                    buffer.append(datum.getBrandName());
                    replace= replaceQueryString(param,datum.getBrandId()+"","brandId");
                }
                navVo.setNavValue(buffer.toString());
                navVo.setLink("http://search.gulimall.com/list.html?" + replace);
            }
            navs.add(navVo);
        }


        return result;
    }

    private String replaceQueryString(SearchParam param,String attr,String key) {
        String encode=null;
        try {
            encode=URLEncoder.encode(attr, "utf-8");
            encode=encode.replace("+","%20");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String replace = param.get_queryString().replace("&"+key+"=" + encode, "");
        return replace;
    }

    private SearchRequest buildSearchRequrest(SearchParam searchParam) {
        SearchSourceBuilder sourceBuilder=new SearchSourceBuilder();

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        if (!StringUtils.isEmpty(searchParam.getKeyword())){
            boolQuery.must(QueryBuilders.matchQuery("skuTitle",searchParam.getKeyword()));
        }

        if (searchParam.getCatalog3Id()!=null){
            boolQuery.filter(QueryBuilders.termQuery("catalogId",searchParam.getCatalog3Id()));
        }

        if (searchParam.getBrandId()!=null && searchParam.getBrandId().size()>0){
            boolQuery.filter(QueryBuilders.termQuery("brandId",searchParam.getBrandId()));
        }

        if (searchParam.getAttrs()!=null && searchParam.getAttrs().size()>0){

            for (String attrStr: searchParam.getAttrs()){
                BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
                String[] s = attrStr.split("_");
                String attrId=s[0];
                String[] attrValues = s[1].split(":");
                boolQueryBuilder.must(QueryBuilders.termQuery("attrs.attrId",attrId));
                boolQueryBuilder.must(QueryBuilders.termsQuery("attrs.attrValue",attrId));
                NestedQueryBuilder attrs = QueryBuilders.nestedQuery("attrs", boolQueryBuilder, ScoreMode.None);
                boolQuery.filter(attrs);
            }
        }

        if (searchParam.getHasStock()!=null) {
            boolQuery.filter(QueryBuilders.termQuery("hasStock", searchParam.getHasStock() == 1));
        }

        if (!StringUtils.isEmpty(searchParam.getSkuPrice())){
            RangeQueryBuilder skuPrice = QueryBuilders.rangeQuery("skuPrice");
            String[] s = searchParam.getSkuPrice().split("_");
            if (s.length==2){
                skuPrice.gte(s[0]).lte(s[1]);
            }else if (s.length==1){
                if (searchParam.getSkuPrice().startsWith("_")){
                    skuPrice.lte(s[0]);
                }
                if (searchParam.getSkuPrice().endsWith("_")){
                    skuPrice.gte(s[1]);
                }
            }
            boolQuery.filter(skuPrice);
        }
        sourceBuilder.query(boolQuery);

        if (!StringUtils.isEmpty(searchParam.getSort())){
            String sort = searchParam.getSort();
            String[] s = sort.split("_");
            SortOrder asc = s[1].equalsIgnoreCase("asc") ? SortOrder.ASC : SortOrder.DESC;
            sourceBuilder.sort(s[0], asc);
        }

        sourceBuilder.from((searchParam.getPageNum()-1)*EsConstant.PRODUCT_PAGESIZE);
        sourceBuilder.size(EsConstant.PRODUCT_PAGESIZE);

        if (!StringUtils.isEmpty(searchParam.getKeyword())){
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.field("skuTitle");
            highlightBuilder.preTags("<b style='color:red'>");
            highlightBuilder.postTags("</b>");
            sourceBuilder.highlighter(highlightBuilder);
        }
        TermsAggregationBuilder brand_agg = AggregationBuilders.terms("brand_agg");
        brand_agg.field("brandId").size(50);
        brand_agg.subAggregation(AggregationBuilders.terms("brand_name_agg").field("brandName").size(1));
        brand_agg.subAggregation(AggregationBuilders.terms("brand_img_agg").field("brandImg").size(1));
        sourceBuilder.aggregation(brand_agg);


        TermsAggregationBuilder catalog_agg = AggregationBuilders.terms("catalog_agg").field("catalogId").size(20);
        catalog_agg.subAggregation(AggregationBuilders.terms("catalog_name_agg").field("catalogName").size(1));
        sourceBuilder.aggregation(catalog_agg);

        NestedAggregationBuilder attr_agg = AggregationBuilders.nested("attr_agg", "attrs");
        TermsAggregationBuilder attr_id_agg = AggregationBuilders.terms("attr_id_agg").field("attr.attrId");
        attr_id_agg.subAggregation(AggregationBuilders.terms("attr_name_agg").field("attrs.attrName").size(1));
        attr_id_agg.subAggregation(AggregationBuilders.terms("attr_value_agg").field("attrs.attrValue").size(50));
        attr_agg.subAggregation(attr_id_agg);
        sourceBuilder.aggregation(attr_agg);

        SearchRequest searchRequest = new SearchRequest(new String[]{EsConstant.PRODUCT_INDEX}, sourceBuilder);
        return searchRequest;
    }
}
