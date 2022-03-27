package com.xiaomao6.xiaomaoproduct.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName Category2Vo
 * @Description 简介
 * @Author Micah
 * @Date 2022/2/27 0:08
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category2Vo {

    private String catalog1Id;

    private List<Catalog3Vo> catalog3List;
    private String id;
    private String name;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Catalog3Vo {

        private String catalog2Id;
        private String id;
        private String name;

    }
}
