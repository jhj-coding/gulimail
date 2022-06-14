package com.jhj.gulimall.search.service.imp;

import com.alibaba.fastjson.JSON;
import com.jhj.common.to.es.SkuEsModel;
import com.jhj.gulimall.search.config.GulimallElasticSearchConfig;
import com.jhj.gulimall.search.constant.EsConstant;
import com.jhj.gulimall.search.service.ProductSaveService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @Description ProductSaveServiceImp
 * @Author jhj
 * @Date 2022/6/1410:26
 **/
@Slf4j
@Service
public class ProductSaveServiceImp implements ProductSaveService {
    @Resource
    RestHighLevelClient restHighLevelClient;
    @Override
    public Boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException {

        //给es建立索引，建立好映射关系
        BulkRequest bulkRequest = new BulkRequest();
        for (SkuEsModel skuEsModel : skuEsModels) {
            IndexRequest indexRequest = new IndexRequest(EsConstant.PRODUCT_INDEX);
            indexRequest.id(skuEsModel.getSkuId().toString());
            indexRequest.source(JSON.toJSONString(skuEsModel), XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, GulimallElasticSearchConfig.COMMON_OPTIONS);
        boolean b = bulk.hasFailures();
        List<String> collect = Arrays.stream(bulk.getItems()).map(item -> {
            return item.getId();
        }).collect(Collectors.toList());
        log.info("商品上架成功{},返回数据{}",collect,bulk.toString());
        return b;
    }
}
