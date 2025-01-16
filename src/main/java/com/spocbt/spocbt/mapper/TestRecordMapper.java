package com.spocbt.spocbt.mapper;


import com.spocbt.spocbt.dto.TestRecord;

import java.util.List;

public interface TestRecordMapper {

    int save(TestRecord testRecord);

    List<TestRecord> findByMemberCode(String memberCode);

}
