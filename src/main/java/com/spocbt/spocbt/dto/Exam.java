package com.spocbt.spocbt.dto;

import lombok.Data;

@Data
public class Exam {

    private String examCode;
    private String examTitle;
    private int requiredCnt;
    private int electiveOptionCnt;
    private int electiveCnt;
    private int totalPassingScore;

    private String err = "";

}
