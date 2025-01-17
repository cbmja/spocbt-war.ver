package com.spocbt.spocbt.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spocbt.spocbt.controller.Score;
import com.spocbt.spocbt.dto.*;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestService {

    private final SqlSessionTemplate sql;
    private static final Logger logger = LoggerFactory.getLogger(TestService.class);


    // 24-12-29 : ok--1
    // 24-01-03 : ok--2
    @Transactional
    public Map<String , Object> getExamList(String examCode,String sort){
        Map<String , Object> result = new HashMap<>();

        Exam exam = sql.selectOne("com.spocbt.spocbt.mapper.ExamMapper.findByExamCode",examCode);
        if(exam == null){
            result.put("result" , "err");
            return result;
        }

        UpdateExam updateExam = new UpdateExam();
        updateExam.setExamCode(examCode);
        updateExam.setSort(sort);

        List<UpdateExam> updateExamList = sql.selectList("com.spocbt.spocbt.mapper.UpdateExamMapper.findByExamCode",updateExam);
        if(updateExamList == null){
            result.put("result" , "err");
            return result;
        }
        result.put("result" , "success");
        result.put("exam" , exam);
        result.put("updateExmaList" , updateExamList);
        //throw new RuntimeException();
        return result;
    }


    // 24-12-29 : ok--1
    // 24-01-03 : ok--2
    public UpdateExam findUExam(UpdateExam exam){

        UpdateExam updateExam = sql.selectOne("com.spocbt.spocbt.mapper.UpdateExamMapper.findUpdateExam",exam);

        if(updateExam == null){
            updateExam = new UpdateExam();
            updateExam.setErr("noType");
        }

        return updateExam;

    }


    // 24-12-29 : ok--1
    // 24-01-03 : ok--2
    @Transactional
    public Map<String , Object> getSubjects(String elesubjects , String reqsubjects , String examCode){

        String[] ele = elesubjects.split("/"); // 선택과목
        List<Subject> eleSub = new ArrayList<>(); // 선택과목 data
            for(String sub : ele) {
                if (!sub.isBlank()) {
                    Subject s = sql.selectOne("com.spocbt.spocbt.mapper.SubjectMapper.findBySubjectCode",sub);
                    eleSub.add(s);
                }
            }
        eleSub.sort(Comparator.comparing(Subject::getWeight).reversed()); // 과목 정렬

        String[] req = reqsubjects.split("/"); // 필수과목
        List<Subject> reqSub = new ArrayList<>(); // 필수과목 data
            for(String sub : req) {
                if (!sub.isBlank()) {
                    Subject s = sql.selectOne("com.spocbt.spocbt.mapper.SubjectMapper.findBySubjectCode",sub);
                    reqSub.add(s);
                }
            }
        reqSub.sort(Comparator.comparing(Subject::getWeight).reversed()); // 과목 정렬


        if(eleSub.isEmpty()){
            Subject s = new Subject();
            s.setSubjectName("선택과목이 없는 시험입니다. 필수과목만 응시하세요.");
            eleSub.add(s);
        }
        if(reqSub.isEmpty()){
            Subject s = new Subject();
            s.setSubjectName("필수과목이 없는 시험입니다. 선택과목만 응시하세요.");
            reqSub.add(s);
        }


        Map<String , Object> result = new HashMap<>();
        result.put("ele" , eleSub);
        result.put("req" , reqSub);
        Exam exam = sql.selectOne("com.spocbt.spocbt.mapper.ExamMapper.findByExamCode",examCode);
        result.put("exam" , exam);

        return result;
    }


    // 24-12-29 : ok--1
    // 24-01-03 : ok--2
    @Transactional
    public Map<String , List<Score>> getScore(String examCode , String type , String year , List<String> eleList , List<String> reqList , Map<String , List<String>> myAnswerList , String memberCode) throws JsonProcessingException {
        List<Score> result = new ArrayList<>(); // 과목별 점수 , 통과여부
        Map<String , List<AnswerDetail>> answerList = new HashMap<>();
        for(String sub : eleList){
            answerList.put(sub , new ArrayList<>());
        }
        for(String sub : reqList){
            answerList.put(sub , new ArrayList<>());
        }
        List<Score> tsl = new ArrayList<>(); // 응시 정보 상세
        int total = 0;
        boolean isPassed = true;
        for(String subCode : answerList.keySet()){
    
            AnswerMaster form = new AnswerMaster();
            form.setType(type);
            form.setYear(year);
            form.setExamSubjectCode("%"+examCode+"_"+subCode+"/%");

            // answerMasterCode get함
            AnswerMaster answerMaster = sql.selectOne("com.spocbt.spocbt.mapper.AnswerMasterMapper.findAnswerMaster",form);
            ObjectMapper mapper = new ObjectMapper();
            List<AnswerDetail> adList = mapper.readValue(answerMaster.getAnswerDetail(), new TypeReference<List<AnswerDetail>>() {});

            adList.sort(Comparator.comparing(AnswerDetail::getQuestionNo));

            // 내가 제출한 답안
            List<String> mySubAnswer = myAnswerList.get(subCode);
            Collections.sort(mySubAnswer, Comparator.comparing((String s) -> Integer.parseInt(s.split("_")[1])));

            // 모두 고르시오 문제의 경우  // ex) SE_7_1 , SE_7_2 이런식으로 있는 것을 SE_7_12 이렇게 하나로 만들어 주는 작업
            Map<String, List<String>> grouped = mySubAnswer.stream()
                    .collect(Collectors.groupingBy(s -> s.split("_")[0] + "_" + s.split("_")[1]));

            // 그룹화된 값을 병합
            List<String> mergedList = grouped.entrySet().stream()
                    .map(entry -> {
                        String key = entry.getKey(); // 첫 번째와 두 번째 숫자의 조합
                        String mergedThirdNumbers = entry.getValue().stream()
                                .map(s -> s.split("_")[2]) // 세 번째 숫자 추출
                                .sorted() // 정렬 (선택 사항)
                                .collect(Collectors.joining("")); // 병합
                        return key + "_" + mergedThirdNumbers;
                    })
                    .collect(Collectors.toList());

            Collections.sort(mergedList, Comparator.comparing((String s) -> Integer.parseInt(s.split("_")[1])));


            System.out.println("==========[ "+subCode+" ]==========");
            Subject sub = sql.selectOne("com.spocbt.spocbt.mapper.SubjectMapper.findBySubjectCode",subCode);

            int subScore = 0;
            for(AnswerDetail sa : adList){
                Score sc = new Score();
                sc.setSubTitle(sub.getSubjectName());
                sc.setSubCode(sub.getSubjectCode());

                int qNo = sa.getQuestionNo();
                String aNo = sa.getAnswerNo()+"";
                sc.setQuestionNo(qNo);
                sc.setAnswerNo(sa.getAnswerNo());
                for(String ma : mergedList){

                    int myQNo = Integer.parseInt(ma.split("_")[1]);
                    String myANo = ma.split("_")[2];

                    if(qNo == myQNo){
                        sc.setQuestionNo(qNo);
                        sc.setMyAnswerNo(Integer.parseInt(myANo));
                        sc.setAnswerNo(sa.getAnswerNo());
                        if(aNo.equals(myANo)){
                            subScore += sa.getPoint();
                            sc.setResult("O");
                            //System.out.println("["+qNo+"] 번 내답 : ("+myANo+") / 정답 : ("+aNo+") / O /점수 : "+subScore);
                        }else {
                            sc.setResult("X");
                            //System.out.println("["+qNo+"] 번 내답 : ("+myANo+") / 정답 : ("+aNo+") / X /점수 : "+subScore);
                        }
                        break;
                    }

                }
                tsl.add(sc);
            }

            System.out.println("==========[ "+sub.getSubjectName()+" : "+subScore+" ]==========");
            Score s = new Score();
            s.setSubTitle(sub.getSubjectName());
            s.setScore(subScore);
            s.setResult(subScore >= 40 ? "P":"F");
            if(subScore < 40){
                isPassed = false;
            }
            s.setSubCode(subCode);
            result.add(s);
            total += subScore;
        }

        int subCnt = result.size();
        int passedScore = subCnt * 100 * 6 / 10;
        TestRecord testRecord = new TestRecord();
        testRecord.setExamCode(examCode);
        testRecord.setYear(year);
        testRecord.setType(type);
        testRecord.setTestScore(total);
        testRecord.setMemberCode(memberCode);
        boolean passed = isPassed && total >= passedScore;
        testRecord.setPassed(passed);
        sql.insert("com.spocbt.spocbt.mapper.TestRecordMapper.save",testRecord);
        logger.info("---------------------------save test record : "+testRecord.toString());

        // tsl -> 시험 상세 응시정보 list
        // tsl -> 시험 상세 응시정보 list
        List<TestDetail> testDetails = new ArrayList<>();
        Map<String , String> testDetailInfo = new HashMap<>();
        int i = 1;
        for(Score s : tsl){
            TestDetail testDetail = new TestDetail();
            testDetail.setSubjectCode(s.getSubCode());
            testDetail.setQuestionNo(s.getQuestionNo());
            testDetail.setMySubmitNo(s.getMyAnswerNo());
            testDetail.setAnswerNo(s.getAnswerNo());
            testDetail.setResult(s.getResult().equals("O"));
            testDetails.add(testDetail);
            if(i > 1 && i % 20 == 0){
                testDetails.sort(Comparator.comparing(TestDetail::getQuestionNo));
                ObjectMapper mapper = new ObjectMapper();
                String jsonString = mapper.writeValueAsString(testDetails);
                testDetailInfo.put(s.getSubCode() , jsonString);
                testDetails = new ArrayList<>();
            }
            i++;
        }

        int testRecordCode = testRecord.getTestCode();

        for(Score score : result){
            TestSubjectRecord tsr = new TestSubjectRecord();
            tsr.setTestCode(testRecordCode);
            tsr.setSubjectCode(score.getSubCode());
            tsr.setSubjectScore(score.getScore());
            tsr.setPassed(score.getScore() >= 40);
            tsr.setYear(year);
            tsr.setType(type);
            tsr.setTestDetail(testDetailInfo.get(score.getSubCode()));
            sql.insert("com.spocbt.spocbt.mapper.TestSubjectRecordMapper.save",tsr);
            //logger.info("---------------------------save test subject record : "+tsr.toString()); // 로그파일 용량부족할듯
        }

        Map<String , List<Score>> lr = new HashMap<>();
        lr.put("subScore" , result);
        lr.put("detScore",tsl);
        //throw new RuntimeException();
        return lr;
    }


    // 24-01-03 : ok--1
    @Transactional
    public Map<String , List<Score>> getTestDetails(String testCode) throws Exception {

        int tCode = Integer.parseInt(testCode);

        List<TestSubjectRecord> trList = sql.selectList("com.spocbt.spocbt.mapper.TestSubjectRecordMapper.findByTestCode",tCode);

        Map<String , List<TestDetail>> res = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        List<Score> subScore = new ArrayList<>();
        List<Score> detScore = new ArrayList<>();
        for(TestSubjectRecord tsr : trList){
            Score s = new Score();

            Subject sub = sql.selectOne("com.spocbt.spocbt.mapper.SubjectMapper.findBySubjectCode",tsr.getSubjectCode());
            s.setSubTitle(sub.getSubjectName());
            s.setScore(tsr.getSubjectScore());
            s.setResult(tsr.isPassed()?"T":"F");
            s.setSubCode(sub.getSubjectCode());
            subScore.add(s);
            String detailStr = tsr.getTestDetail();
            List<TestDetail> dtList = mapper.readValue(detailStr, new TypeReference<List<TestDetail>>() {});
            dtList.sort(Comparator.comparing(TestDetail::getQuestionNo));
            res.put(sub.getSubjectCode() , dtList);


            for(TestDetail td : dtList){
                Score sc = new Score();
                sc.setSubTitle(sub.getSubjectName());
                sc.setResult(td.getAnswerNo() == td.getMySubmitNo() ? "O":"X");
                sc.setQuestionNo(td.getQuestionNo());
                sc.setMyAnswerNo(td.getMySubmitNo());
                sc.setAnswerNo(td.getMySubmitNo());
                sc.setSubCode(sub.getSubjectCode());
                detScore.add(sc);
            }


            //throw new Exception();
        }

        Map<String , List<Score>> res2 = new HashMap<>();
        res2.put("subScore",subScore);
        res2.put("detScore",detScore);
        return res2;
    }






}
