package com.xiaomao6.search.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName ElasticSearchConfig
 * @Description 简介
 * @Author Micah
 * @Date 2021/10/8 21:51
 * @Version 1.0
 **/
@Configuration
public class ElasticSearchConfig {
    //默认配置
    public static final RequestOptions COMMON_OPTIONS;

    /**
     * 放置默认配置
     */
    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
//        builder.addHeader("Authorization", "Bearer " + TOKEN);
//        builder.setHttpAsyncResponseConsumerFactory(
//                new HttpAsyncResponseConsumerFactory
//                        .HeapBufferedResponseConsumerFactory(30 * 1024 * 1024 * 1024));
        COMMON_OPTIONS = builder.build();
    }
    @Bean
    public RestHighLevelClient getClient(){
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("1.117.57.143", 9200, "http")));
    }
}
