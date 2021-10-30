package com.xiaomao6.search;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiaomao6.search.config.ElasticSearchConfig;
import com.xiaomao6.search.entity.BankEntity;
import lombok.Data;
import lombok.experimental.Accessors;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Avg;
import org.elasticsearch.search.aggregations.metrics.AvgAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@SpringBootTest
class XiaomaoSearchApplicationTests {

    @Resource
    RestHighLevelClient restHighLevelClient;

    @Test
    void contextLoads() {
        System.out.println(restHighLevelClient);
    }

    @Test
    void searchIndex() throws IOException {

        //创建一个查询语句
        SearchRequest searchRequest = new SearchRequest();
        //设置查询哪个索引
        searchRequest.indices("bank");
        //创建一个查询条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //查询全部
        searchSourceBuilder
                .query(QueryBuilders.matchAllQuery());
        //设置年龄aggs terms
        TermsAggregationBuilder age = AggregationBuilders
                .terms("age")
                .field("age")
                .size(10);
        //设置平均价格avg
        AvgAggregationBuilder balance = AggregationBuilders
                .avg("balance")
                .field("balance");
        //插入到条件
        searchSourceBuilder
                .aggregation(age)
                .aggregation(balance);
        //将查询条件添加到查询语句
        searchRequest.source(searchSourceBuilder);
        System.out.println("查询参数: "+searchRequest);
        //执行查询
        SearchResponse search = restHighLevelClient.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);

        System.out.println("查询获取: "+search);
        //可以获取索引hits
        SearchHits hits = search.getHits();
        //遍历转化为对象输出
        hits.forEach(e->{
            BankEntity bankEntity = JSONObject.parseObject(e.getSourceAsString(), BankEntity.class);
            System.out.println(bankEntity);
        });

        //获得所以aggs
        Aggregations aggregations = search.getAggregations();
        //获取age Terms
        Terms age1 = aggregations.get("age");
        //获取buckets 遍历输出
        List<? extends Terms.Bucket> buckets = age1.getBuckets();
        buckets.forEach(e->{
            System.out.println(e.getKey()+"   "+e.getDocCount());
        });

        //获取 balance avg
        Avg balance1 = aggregations.get("balance");
        //输出值
        System.out.println(balance1.getValue());


    }

    @Test
    void addIndex() throws IOException {
        //索引名字为users
        IndexRequest request = new IndexRequest("users");
        //设置id
        request.id("1");
        Users user = new Users()//创建一个user对象,并且赋值
                .setAge(18)
                .setGender("M")
                .setName("槐林sama");
        String s = JSON.toJSONString(user);//转换为json字符串
        request.source(s, XContentType.JSON);//设置字符串为对应键值对,并且确定类型为json
        //使用客户端实例的index方法 将索引信息和配置信息导入发送
        restHighLevelClient.index(request, ElasticSearchConfig.COMMON_OPTIONS);
    }
    @Data
    @Accessors(chain = true)
    public static class Users{
        //名字
        private String name;
        //性别
        private String gender;
        //年龄
        private Integer age;
    }

}
