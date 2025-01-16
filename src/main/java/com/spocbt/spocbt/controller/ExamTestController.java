package com.spocbt.spocbt.controller;


import com.spocbt.spocbt.dto.Exam;
import com.spocbt.spocbt.dto.Subject;
import com.spocbt.spocbt.dto.UpdateExam;
import com.spocbt.spocbt.service.TestService;
import jakarta.servlet.ServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/exam")
@RequiredArgsConstructor
public class ExamTestController {

    private final TestService testService;

    // 24-12-29 : ok--1
    // 24-01-03 : ok--2
    // 모의시험 목록
    @GetMapping("/list")
    public String test (@RequestParam(name ="examCode",required = false ,defaultValue = "LSI_2") String examCode
                        ,@RequestParam(name ="sort",required = false ,defaultValue = "DESC") String sort
                        , Model model, ServletRequest request){


        Map<String , Object> res = new HashMap<>();
        try{
            res = testService.getExamList(examCode,sort);

        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("isErr" , "1");
            model.addAttribute("examTitle" , "서버 에러입니다. 다시 시도해주세요.");
            return "util/prepare";
        }

        if(!((String)res.get("result")).equals("success")){
            model.addAttribute("isErr" , "1");
            model.addAttribute("examTitle" , "서버 에러입니다. 다시 시도해주세요.");
            return "util/prepare";
        }
        int currentYear = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();
        int yy = currentMonth <= 5 ? currentYear - 1 : currentYear;

        List<String> updateYears = new ArrayList<>();

        if(sort.toUpperCase().equals("DESC")){
            for(int i = yy; i>=2015; i--){
                updateYears.add(i+"");
            }
        }else{
            for(int i = 2015; i<=yy; i++){
                updateYears.add(i+"");
            }
        }


        List<UpdateExam> list = (List<UpdateExam>)res.get("updateExmaList");
        //List<String> realUpdateYear = new ArrayList<>();
        String ruy = "";
        for (UpdateExam year : list){
            //realUpdateYear.add(year.getYear());
            ruy += "_"+year.getYear();
        }

        model.addAttribute("selectedNav" , "test");
        model.addAttribute("isLogin" , (String)request.getAttribute("isLogin")); // 로그인 여부
        model.addAttribute("exam" , (Exam)res.get("exam")); // 시험 종류
        // model.addAttribute("examCode" , examCode);
        model.addAttribute("realUpdateYear" , ruy); // 실제 서버에 업로드 된 시험 년도 2015_2016_2018 ... 형식
        model.addAttribute("updateYears",updateYears); // 2015~현재 년도
        model.addAttribute("navText","이제 프린트는 그만!! 기출문제를 온라인으로 풀어보세요!!");

        if(list.isEmpty()){
            model.addAttribute("isErr" , "0");
            model.addAttribute("examTitle" , ((Exam)res.get("exam")).getExamTitle());
            return "util/prepare";
        }

        return "view/examList";
    }

    // 24-12-29 : ok--1
    // 24-01-03 : ok--2
    // 시험지
    @GetMapping("/test")
    public String testg(@RequestParam(name ="year",required = false ,defaultValue = "") String year
                        ,@RequestParam(name ="type",required = false ,defaultValue = "") String type
                        ,@RequestParam(name ="examCode",required = false ,defaultValue = "") String examCode
                        ,@RequestParam(name ="eleSubs",required = false ,defaultValue = "") String eleSubs
                        ,@RequestParam(name ="reqSubs",required = false ,defaultValue = "") String reqSubs
                        ,Model model, ServletRequest request){

        if(year.isEmpty() || type.isEmpty() || examCode.isEmpty()){
            model.addAttribute("isErr" , "1");
            model.addAttribute("examTitle" , "서버 에러입니다. 다시 시도해주세요.(시험 정보 누락)");
            return "util/prepare";
        }else if(eleSubs.isEmpty() && reqSubs.isEmpty()){
            model.addAttribute("isErr" , "1");
            model.addAttribute("examTitle" , "서버 에러입니다. 다시 시도해주세요.(응시과목 정보 누락)");
            return "util/prepare";
        }

        UpdateExam form = new UpdateExam();
        form.setExamCode(examCode);
        form.setYear(year);
        form.setType(type);

        UpdateExam updateExam = testService.findUExam(form);
        
        if(updateExam.getErr().equals("err")){
            // 서버 에러일 경우 -------------------------------------------------------------------------------------------
            model.addAttribute("isErr" , "1");
            model.addAttribute("examTitle" , "서버 에러입니다. 다시 시도해주세요.");
            return "util/prepare";
        } else if (updateExam.getErr().equals("noType")) {
            // 해당 타입이 업로드 전일 경우 --------------------------------------------------------------------------------
            model.addAttribute("isErr" , "1");
            String t = type.equals("A")?"B":"A";
            model.addAttribute("examTitle" , "[ "+type+" ]타입은 준비중입니다. [ "+t+" ]타입을 선택해주세요.");
            return "util/prepare";
        }

        Map<String , Object> testData = new HashMap<>();
        try{
            testData = testService.getSubjects(eleSubs,reqSubs,examCode);

        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("isErr" , "1");
            model.addAttribute("examTitle" , "서버 에러입니다. 다시 시도해주세요.");
            return "util/prepare";
        }

        model.addAttribute("isLogin" , (String)request.getAttribute("isLogin")); //
        model.addAttribute("elesubs" , eleSubs); //
        model.addAttribute("reqsubs" , reqSubs); //
        model.addAttribute("eles" , (List<Subject>)testData.get("ele"));
        model.addAttribute("reqs" , (List<Subject>)testData.get("req"));
        model.addAttribute("year",year); //
        model.addAttribute("type",type); //
        model.addAttribute("exam",(Exam)testData.get("exam"));
        model.addAttribute("memberCode" , (String)request.getAttribute("memberCode"));

        return "view/exam/"+updateExam.getFileName();
    }

    // 24-12-29 : ok--1
    // 24-01-03 : ok--2
    // 제출
    @PostMapping("/submit")
    @ResponseBody
    public Map<String , List<Score>> testSubmit (@RequestBody Map<String,Object> form, ServletRequest request,Model model){
        // model.addAttribute("isLogin" , (String)request.getAttribute("isLogin"));
        String examCode = (String)form.get("examCode") == null ? "" : (String)form.get("examCode");
        String year = (String)form.get("year") == null ? "" : (String)form.get("year");
        String type = (String)form.get("type") == null ? "" : (String)form.get("type");
        String electiveSubjects = (String)form.get("electiveSubjects") == null ? "" : (String)form.get("electiveSubjects");
        String requiredSubjects = (String)form.get("requiredSubjects") == null ? "" : (String)form.get("requiredSubjects");
        String memberCode = (String)form.get("memberCode") == null ? "" : (String)form.get("memberCode");
        // 내가 제출한 답안 '과목코드_문제번호_선택보기' 형태의 문자열 list
        // ex) SE_7_1 -> 스포츠교육학 7번문제 1번 보기 선택
        List<String> myAnswer = (List<String>)form.get("form") == null ? new ArrayList<>() : (List<String>)form.get("form");


        // 과목코드/과목코드/.. 형태의 문자열을 list로 변환
        List<String> eleList = Arrays.stream(electiveSubjects.split("/"))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
        List<String> reqList = Arrays.stream(requiredSubjects.split("/"))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        // 과목순 , 문제번호순 으로 정렬
        Collections.sort(myAnswer, Comparator
                .comparing((String s) -> s.split("_")[0]) // 첫 번째 `_` 앞의 문자로 정렬
                .thenComparing(s -> Integer.parseInt(s.split("_")[1])) // 두 번째 `_` 뒤의 숫자로 정렬
        );

        Map<String , List<String>> myAnswerList = new HashMap<>();

        for(String sub : eleList){
            myAnswerList.put(sub , new ArrayList<>());
        }
        for(String sub : reqList){
            myAnswerList.put(sub , new ArrayList<>());
        }
        // 선택과목 아닌 것중 보기에서 선택한 것 제거
        for(String myanswer : myAnswer){
           String a = myanswer.split("_")[0];
           if(myAnswerList.get(a) != null){
               myAnswerList.get(a).add(myanswer);
           }
        }
        Map<String , List<Score>> score = new HashMap<>();
        try{
            score = testService.getScore(examCode,type,year,eleList,reqList,myAnswerList , memberCode);
        }catch (Exception e){
            e.printStackTrace();
            List<Score> errs = new ArrayList<>();
            Score s = new Score();
            s.setErr("err");
            score.put("err" , errs);
            return score;
        }


        return score;
    }

    // 24-01-03 : ok--1
    // 상세
    @GetMapping("/test/detail")
    @ResponseBody
    public Map<String , List<Score>> testDetail(@RequestParam(name = "testCode" , required = false , defaultValue = "") String testCode){
        Map<String , List<Score>> testSubjectRecords;

        if(testCode.isBlank()){
            testSubjectRecords = new HashMap<>();
            List<Score> t = new ArrayList<>();
            t.add(new Score());
            testSubjectRecords.put("err" , t);
        }

        try{
            testSubjectRecords = testService.getTestDetails(testCode);
        }catch (Exception e){
            e.printStackTrace();
            testSubjectRecords = new HashMap<>();
            List<Score> t = new ArrayList<>();
            t.add(new Score());
            testSubjectRecords.put("err" , t);
        }


        return testSubjectRecords;
    }



}
