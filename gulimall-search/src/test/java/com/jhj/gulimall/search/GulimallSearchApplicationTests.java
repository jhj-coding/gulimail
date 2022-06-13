package com.jhj.gulimall.search;

import com.alibaba.fastjson.JSON;
import com.jhj.gulimall.search.config.GulimallElasticSearchConfig;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GulimallSearchApplicationTests {

    class User{
        private String username;
        private String gender;
        private Integer age;

        public void setUsername(String username) {
            this.username = username;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }
    @Resource
    private RestHighLevelClient restHighLevelClient;
    @Test
    /**
     * 保存 /更新
     */
    public void saveTest() throws IOException {
        IndexRequest users = new IndexRequest("users");
        //不设置会自动生成
        users.id("1");
        //1. 键值对
//        users.source("userName","zhangsan","age",18);
        //2. 对象 json转 推荐使用
        User user = new User();
        String s = JSON.toJSONString(user);
        user.setUsername("靳豪杰");
        user.setAge(22);
        user.setGender("男");
        users.source(s, XContentType.JSON);
//执行操作
        IndexResponse index = restHighLevelClient.index(users, GulimallElasticSearchConfig.COMMON_OPTIONS);
        System.out.println(index);
    }

    @Test
    /**
     * 搜索
     */
    public void searchTest() throws IOException {
        //创建请求
        SearchRequest searchRequest = new SearchRequest();
        //指定索引 库
        searchRequest.indices("users");
        //指定DSL 检索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchRequest.source(searchSourceBuilder);
        //执行
        restHighLevelClient.search(searchRequest,GulimallElasticSearchConfig.COMMON_OPTIONS);
    }

}
