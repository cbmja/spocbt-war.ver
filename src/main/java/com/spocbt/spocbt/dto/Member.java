package com.spocbt.spocbt.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Member {

    private String memberCode = "";
    private String loginType;
    private String loginId;
    private String email;
    private String name;
    private String job;
    private String state;
    private LocalDateTime joinAt;
    private String age;

}
