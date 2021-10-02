package com.xiaomao6.common.to;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class MemberPrice {

    private Long id;
    private String name;
    private BigDecimal price;


}
