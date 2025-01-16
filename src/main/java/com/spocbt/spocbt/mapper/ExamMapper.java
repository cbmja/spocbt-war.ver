package com.spocbt.spocbt.mapper;


import com.spocbt.spocbt.dto.Exam;

public interface ExamMapper {


    Exam findByExamCode(String examCode);

}
