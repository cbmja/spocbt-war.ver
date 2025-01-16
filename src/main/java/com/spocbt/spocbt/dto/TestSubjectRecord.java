package com.spocbt.spocbt.dto;

import lombok.Data;

@Data
public class TestSubjectRecord {

    private int testSubjectCode;
    private int testCode;
    private String subjectCode;
    private int SubjectScore;
    private boolean isPassed;
    private String year;
    private String type;
    private String testDetail;

}
