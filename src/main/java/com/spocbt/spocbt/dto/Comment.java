package com.spocbt.spocbt.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Comment {

    private int commentSeq;
    private String memberCode;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private int boardSeq;
    private String memberName;

    private String err = "";

}
