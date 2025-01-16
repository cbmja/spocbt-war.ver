package com.spocbt.spocbt.mapper;


import com.spocbt.spocbt.dto.Subject;

public interface SubjectMapper {

    Subject findBySubjectCode(String subjectCode);

}
