package com.xiaomao6.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.xiaomao6.common.to.es.SkuEsModel;
import com.xiaomao6.search.config.ElasticSearchConfig;
import com.xiaomao6.search.constant.ESConstant;
import com.xiaomao6.search.service.SaveSkuService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkItemResponse;
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
 * @ClassName SaveSkuServiceImpl
 * @Description 简介
 * @Author Micah
 * @Date 2021/10/18 17:24
 * @Version 1.0
 **/
@Slf4j
@Service
public class SaveSkuServiceImpl implements SaveSkuService {
    @Resource
    RestHighLevelClient restHighLevelClient;
    @Override
    public Boolean saveSkus(List<SkuEsModel> list) throws IOException {
        //创建索引

        //批量插入数据
        //BulkRequest bulkRequest, RequestOptions options
        BulkRequest bulkRequest = new BulkRequest();
        list.forEach(e->{
            IndexRequest indexRequest = new IndexRequest(ESConstant.INDEX);
            //修改id
            indexRequest.id(e.getSkuId().toString());
            String s = JSON.toJSONString(e);
            //插入数据到indexRequest中
            indexRequest.source(s, XContentType.JSON);
            //添加到bulk中
            bulkRequest.add(indexRequest);
        });
        //执行bulk
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, ElasticSearchConfig.COMMON_OPTIONS);

        //再处理bulk的执行结果
        //TODO 如果批量错误

        List<String> collect = Arrays.stream(bulk.getItems()).map(BulkItemResponse::getId).collect(Collectors.toList());
        log.error("商品上架错误:{}",collect);

        return bulk.hasFailures();

    }
}
