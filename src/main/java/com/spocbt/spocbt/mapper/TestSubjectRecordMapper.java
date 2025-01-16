package com.spocbt.spocbt.mapper;


import com.spocbt.spocbt.dto.TestSubjectRecord;

import java.util.List;

public interface TestSubjectRecordMapper {

    int save(TestSubjectRecord testSubjectRecord);

    List<TestSubjectRecord> findByTestCode(int testCode);
}
