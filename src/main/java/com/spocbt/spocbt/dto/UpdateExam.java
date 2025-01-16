package com.spocbt.spocbt.dto;

import lombok.Data;

@Data
public class UpdateExam {

    private int updateExamCode;
    private String examCode;
    private String year;
    private String type;
    private String path;
    private String fileName="";

    private String sort = "DESC";
    private String err = "";


}
