package com.spocbt.spocbt.controller;

import lombok.Data;

@Data
public class Score {


    private String subTitle;
    private int score;
    private String result="X";

    private int questionNo;
    private int myAnswerNo=0;
    private int AnswerNo;
    private String subCode;

    private String err;

}
