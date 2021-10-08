package com.xiaomao6.search;

import com.alibaba.fastjson.JSON;
import com.xiaomao6.search.config.ElasticSearchConfig;
import lombok.Data;
import lombok.experimental.Accessors;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;

@SpringBootTest
class XiaomaoSearchApplicationTests {

    @Resource
    RestHighLevelClient restHighLevelClient;

    @Test
    void contextLoads() {
        System.out.println(restHighLevelClient);
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
