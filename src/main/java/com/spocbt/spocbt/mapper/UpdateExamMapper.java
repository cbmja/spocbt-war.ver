package com.spocbt.spocbt.mapper;


import com.spocbt.spocbt.dto.UpdateExam;

import java.util.List;

public interface UpdateExamMapper {

    List<UpdateExam> findByExamCode(UpdateExam updateExam);

    UpdateExam findUpdateExam(UpdateExam updateExam);
}
