package com.lhb.studentsystem.model;

import lombok.Data;
//登录用户
@Data
public class Admin {
    private Integer Id;
    private String username;
    private String password;
    private String name;
}
