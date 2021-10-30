package com.xiaomao6.search.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @ClassName BankEntity
 * @Description 简介
 * @Author Micah
 * @Date 2021/10/14 0:18
 * @Version 1.0
 **/
@Data
@Accessors(chain = true)
public class BankEntity {
    private int account_number;
    private int balance;
    private String firstname;
    private String lastname;
    private int age;
    private String gender;
    private String address;
    private String employer;
    private String email;
    private String city;
    private String state;
}
