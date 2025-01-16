package com.spocbt.spocbt.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardDetail {

    private String memberCode;
    private String title;
    private String content;
    private String boardType;
    private LocalDateTime updateAt;
    private LocalDateTime createAt;
    private int viewCnt;
    private int boardSeq;
    private String memberName;

    private String searchType;
    private String search;
    private String page;
    private String sort;
    private int startNum;
    private int pageElement;



}
