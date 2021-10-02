
package com.xiaomao6.xiaomaoproduct.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Attr {
    private Long attrId;
    private String attrName;
    private String attrValue;
}
