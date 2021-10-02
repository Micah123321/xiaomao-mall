
package com.xiaomao6.xiaomaoproduct.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BaseAttrs {

    private Long attrId;
    private String attrValues;
    private Integer showDesc;

}
