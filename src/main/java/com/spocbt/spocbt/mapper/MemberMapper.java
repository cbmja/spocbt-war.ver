package com.spocbt.spocbt.mapper;


import com.spocbt.spocbt.dto.Member;

public interface MemberMapper {

    Member isUser(Member member);

    int save(Member member);

    Member findByMemberCode(String memberCode);

    Member checkName(String name);
}
