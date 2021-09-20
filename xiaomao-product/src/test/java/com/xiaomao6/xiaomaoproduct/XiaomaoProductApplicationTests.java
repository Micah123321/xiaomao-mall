package com.xiaomao6.xiaomaoproduct;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaomao6.xiaomaoproduct.entity.BrandEntity;
import com.xiaomao6.xiaomaoproduct.service.BrandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class XiaomaoProductApplicationTests {
    @Autowired
    private BrandService brandService;


    @Test
    void contextLoads() {
//        brandService.save(new BrandEntity().setName("华为"));
//        boolean flag = brandService.updateById(new BrandEntity().setBrandId(1L).setDescript("我是中国之光"));
//        System.out.println(flag);
        List<BrandEntity> brandId = brandService.list(new QueryWrapper<BrandEntity>().eq("brand_id", 1));
        brandId.forEach(e-> System.out.println(e.toString()));
    }

}
