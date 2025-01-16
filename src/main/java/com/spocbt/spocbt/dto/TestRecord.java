package com.spocbt.spocbt.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TestRecord {

    private int testCode;
    private String examCode;
    private String memberCode = "test_member_code";
    private String year;
    private String type;
    private int testScore;
    private boolean isPassed;
    private LocalDateTime testAt;

    private String testDate;
    private int no;
    private String examTitle;
    private String pn;

    private String err="";

}
