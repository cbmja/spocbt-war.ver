package com.spocbt.spocbt.service;


import com.spocbt.spocbt.dto.Exam;
import com.spocbt.spocbt.dto.Member;
import com.spocbt.spocbt.dto.TestRecord;
import com.spocbt.spocbt.util.LoginUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final SqlSessionTemplate sql;
    private final LoginUtil loginUtil;
    @Value("${hash.default_secret}")
    private String default_secret;

    @Transactional
    public List<TestRecord> myTestHistory(String memberCode){

        List<TestRecord> history = sql.selectList("com.spocbt.spocbt.mapper.TestRecordMapper.findByMemberCode",memberCode);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd / a hh시mm분");

        int i = 1;
        for(TestRecord t : history){

            Exam exam = sql.selectOne("com.spocbt.spocbt.mapper.ExamMapper.findByExamCode",t.getExamCode());

            t.setTestDate(t.getTestAt().format(formatter));
            t.setNo(i);
            t.setPn(t.isPassed() ? "pass" : "none pass");
            t.setExamTitle(exam.getExamTitle());
            i++;
        }

        return history;
    }

    public String findByMemberCode(String memberCode){

        Member member = sql.selectOne("com.spocbt.spocbt.mapper.MemberMapper.findByMemberCode",memberCode);
        String mc = "";
        if(member != null){
            mc = member.getMemberCode();
        }


        return mc;
    }

    public boolean checkName(String name){

        Member member = sql.selectOne("com.spocbt.spocbt.mapper.MemberMapper.checkName",name);
        if(member == null){
            return true;
        }else{
            return false;
        }

    }


    @Transactional
    public String loginMember(String loginType , String loginId , HttpServletResponse response , String type , Member form){

        
        // 회원인지 아닌지
        Member m = new Member();
        m.setLoginType(loginType);
        m.setLoginId(loginId);
        Member mm = sql.selectOne("com.spocbt.spocbt.mapper.MemberMapper.isUser",m);
        String memberCode = "";

        if(type.equals("join")){
            LocalDateTime now = LocalDateTime.now();

            // 포맷 정의 (년-월-일 시:분:초)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
            // 날짜와 시간 포맷 적용
            String formattedDateTime = now.format(formatter);
            // 나노초 가져오기
            int nanoSeconds = now.getNano();
            int randomNumber = (int) (Math.random() * 100) + 1;
            memberCode = formattedDateTime +"_"+nanoSeconds+"_"+randomNumber;

            form.setMemberCode(memberCode);
            form.setState("active");

            int res = sql.insert("com.spocbt.spocbt.mapper.MemberMapper.save",form);
            if(res <= 0){
                return "err";
            }


            // 로그인
            String ecCookie = loginUtil.encrypt(memberCode);
            Cookie idCookie = new Cookie("idCookie", ecCookie);
            // 쿠키 설정
            idCookie.setHttpOnly(true);
            idCookie.setSecure(false);
            idCookie.setPath("/");
            idCookie.setMaxAge(30 * 60 * 60);

            response.addCookie(idCookie);

            return "join";

        }

        if(mm == null){ //회원가입
            return "join";

        }else{
            memberCode = mm.getMemberCode();
        }
        
        // 로그인
        String ecCookie = loginUtil.encrypt(memberCode);
        Cookie idCookie = new Cookie("idCookie", ecCookie);
        // 쿠키 설정
        idCookie.setHttpOnly(true);
        idCookie.setSecure(false);
        idCookie.setPath("/");
        idCookie.setMaxAge(30 * 60 * 60);

        response.addCookie(idCookie);

        return "login";
    }




}
