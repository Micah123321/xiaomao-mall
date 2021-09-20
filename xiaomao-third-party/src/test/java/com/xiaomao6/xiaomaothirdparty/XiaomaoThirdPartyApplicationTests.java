package com.xiaomao6.xiaomaothirdparty;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.File;

@SpringBootTest
class XiaomaoThirdPartyApplicationTests {
    @Resource
    private OSSClient ossClient;

    @Test
    public void uploadTest() {
        // 创建PutObjectRequest对象:参数 bucket 和 文件名
        PutObjectRequest putObjectRequest = new PutObjectRequest("xiaomao-mall", "b2.png",
                new File("E:\\micah\\markdown\\Pasted image 20210917174150.png"));

        // 上传文件。
        ossClient.putObject(putObjectRequest);

        // 关闭OSSClient。
        ossClient.shutdown();
        System.out.println("上传成功...");
    }
    @Test
    void contextLoads() {
    }

}
