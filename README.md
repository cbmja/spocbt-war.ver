# 📖생활스포츠지도사 기출문제 온라인 풀이 사이트.
### 배포: <http://spocbt.cafe24.com/exam/list>

## 목차

### 1. 프로젝트 소개
### 2. 프로젝트 기간
### 3. 개발 환경
### 4. 개발 인원
### 5. erd
### 6. 디렉터리 구조
### 7. 기능 시연 및 코드 설명
### 8. 느낀점점

<br/>

---

<br/>

## 1. 프로젝트 소개

### 1. '생활스포츠지도사'시험의 온라인 기출문제 풀이 서비스를 제공합니다.
### 2. 내가 응시했던 시험 정보를 확인할 수 있습니다. 
### 3. 시험 정보 취업 정보등을 자유롭게 나눌 수 있는 커뮤니티 기능을 제공합니다.

<br/>

---

<br/>

## 2. 프로젝트 기간 

### 24/12/18 ~ 25/01/15 (약 1달)

<br/>

---

<br/>

## 3. 개발 환경

### 개발환경 : window11 , intelliJ , gradle
### back-end : java17 , spring boot3.3 , mysql , mybatis
### front-end : html5 , css3 , jQuery , Thymeleaf

<br/>

---

<br/>

## 4. 개발 인원 

### 1명

<br/>

---

<br/>

## 5. ERD

![화면 캡처 2025-01-25 124236](https://github.com/user-attachments/assets/c90e7a17-ef69-463c-aae3-a300ad59840b)


<br/>

---

<br/>

## 5. 디렉터리 구조

### back
````

├─java
│  └─com
│      └─spocbt
│          └─spocbt
│              │  ServletInitializer.java
│              │  SpocbtApplication.java
│              │
│              ├─controller
│              │      AdminController.java
│              │      BoardController.java
│              │      ExamTestController.java
│              │      MemberController.java
│              │      Page.java
│              │      Score.java
│              │      Search.java
│              │
│              ├─dto
│              │      AnswerDetail.java
│              │      AnswerMaster.java
│              │      BoardDetail.java
│              │      Comment.java
│              │      Exam.java
│              │      Member.java
│              │      Subject.java
│              │      TestDetail.java
│              │      TestRecord.java
│              │      TestSubjectRecord.java
│              │      UpdateExam.java
│              │
│              ├─filter
│              │      LoginCheck.java
│              │
│              ├─mapper
│              │      AnswerMasterMapper.java
│              │      BoardDetailMapper.java
│              │      CommentMapper.java
│              │      ExamMapper.java
│              │      ExamSubjectMapper.java
│              │      MemberMapper.java
│              │      SubjectMapper.java
│              │      TestRecordMapper.java
│              │      TestSubjectRecordMapper.java
│              │      UpdateExamMapper.java
│              │
│              ├─service
│              │      AdminService.java
│              │      BoardService.java
│              │      MemberService.java
│              │      TestService.java
│              │
│              └─util
│                      JvmCheck.java
│                      LoginDto.java
│                      LoginUtil.java

````

<br/>

---

<br/>

### front
````

│
└─resources
    │  application.yml
    │
    ├─mapper
    │      answer_master.xml
    │      board_detail.xml
    │      comment.xml
    │      exam.xml
    │      exam_subject.xml
    │      member.xml
    │      subject.xml
    │      test_record.xml
    │      test_subject_record.xml
    │      update_exam.xml
    │
    ├─static
    │  ├─css
    │  │  │  common.css
    │  │  │
    │  │  ├─exam
    │  │  │      exam.css
    │  │  │
    │  │  ├─layout
    │  │  │      footer.css
    │  │  │      header.css
    │  │  │      navbar.css
    │  │  │
    │  │  └─view
    │  │          boardDetail.css
    │  │          boardForm.css
    │  │          boardList.css
    │  │          examList.css
    │  │          history.css
    │  │          join.css
    │  │          login.css
    │  │          myBoard.css
    │  │          mypageMenu.css
    │  │
    │  ├─icon
    │  │      profile.svg
    │  │      search.svg
    │  │
    │  └─js
    │      │  jquery-3.7.1.min.js
    │      │
    │      ├─exam
    │      │      exam.js
    │      │
    │      ├─layout
    │      │      footer.js
    │      │      header.js
    │      │      navbar.js
    │      │
    │      └─view
    │              boardDetail.js
    │              boardForm.js
    │              boardLIst.js
    │              examList.js
    │              history.js
    │              join.js
    │              login.js
    │              myBoard.js
    │              mypageMenu.js
    │
    └─templates
        │  sub.txt
        │
        ├─layout
        │      footer.html
        │      header.html
        │      layout.html
        │      navbar.html
        │
        ├─util
        │      prepare.html
        │
        └─view
            │  boardDetail.html
            │  boardForm.html
            │  boardList.html
            │  examList.html
            │  exam_subject_modal.html
            │  index.html
            │  join.html
            │  login.html
            │
            ├─exam
            │      2016.LSI_2.A형.html
            │      2016.LSI_2.B형.html
            │      2018.LSI_2.A형.html
            │      2018.LSI_2.B형.html
            │      2019.LSI_2.A형.html
            │      2019.LSI_2.B형.html
            │      2020.LSI_2.A형.html
            │      2020.LSI_2.B형.html
            │      2021.LSI_2-PSI_2-ASI_2-YSI-SSI.A형.html
            │      2021.LSI_2-PSI_2-ASI_2-YSI-SSI.B형.html
            │      2022.LSI_2-PSI_2-ASI_2-YSI-SSI.A형.html
            │      2022.LSI_2-PSI_2-ASI_2-YSI-SSI.B형.html
            │      2023.LSI_2-PSI_2-ASI_2-YSI-SSI.A형.html
            │      2023.LSI_2-PSI_2-ASI_2-YSI-SSI.B형.html
            │      exambar.html
            │
            └─mypage
                    history.html
                    menu.html
                    myBoard.html


````

<br/>

---

<br/>

## 메인 화면 / 응시 가능 시험 목록

https://github.com/user-attachments/assets/e8595d30-eaef-4050-92ba-bb5deb305805

<br/>

---

<br/>

## 시험 선택시 -> 응시과목 선택 모달

https://github.com/user-attachments/assets/6dd4ad49-a756-40fe-b035-0231dd8778e8

### 과목 태그 코드

![화면 캡처 2025-01-25 131600](https://github.com/user-attachments/assets/06681b31-0202-41cd-b489-a52f6447b870)

```javascript

<div class="table-td table-ex-sub elective-background" id="SP"><span class="sub">스포츠심리학</span><span class="sub-type-badge elective-badge">선택</span></div>

```

### 선택과목을 클릭했을 경우 입니다.

```javascript

$(document).on('click', '.elective-background', function(){

    // 선택한 과목의 과목 코드
    let subjectCode = $(this).attr('id');

    // 현재 클릭한 영역
    let subjectTd = $('#'+subjectCode);

    // 현재 선택한 시험의 선택해야하는 과목 수
    let eleCnt = $('#eleCnt').val();

    // 현재 선택되어있는 과목 수
    let selectedCount = $('.elective-background').filter(function() {
                                 return $(this).attr('data-isselected') === '1';
                             }).length;

    // data 부여
    let isselected = subjectTd.attr('data-isselected');

    // 현재 선택한 과목이 선택되어있지 않았을 경우 -> 선택과목을 모두 선택했을 경우 alert
    // 아닌 경우 선택처리
    if(!isselected || isselected != '1' ){

        if(selectedCount >= eleCnt){
            alert(eleCnt+'과목을 모두 선택하였습니다. 과목 변경을 원하시면 선택한 과목을 취소 후 다른 과목을 선택해 주세요. (다시 클릭하면 취소.)');
            return;
        }

        let rl = subjectTd.find('.elective-badge').removeClass('elective-badge').addClass('selected-badge');
        rl.attr('style' , 'color : #F89321 !important;');

        subjectTd.addClass('selected-subject');
        subjectTd.find('.sub').attr('style' , 'color : #FFFFFF !important;');
        subjectTd.attr('data-isselected','1');



    }else{
    // 현재 선택한 과목이 선택되어 있었던 경우 -> 선택 해제 처리

        subjectTd.find('.sub-type-badge').removeAttr('style');
        subjectTd.find('.sub').removeAttr('style');

        subjectTd.find('.selected-badge').removeClass('selected-badge').addClass('elective-badge');
        subjectTd.removeClass('selected-subject');
        subjectTd.attr('data-isselected','0');

    }
    return;


});

```

<br/>

---

<br/>

## 응시버튼 클릭시 

https://github.com/user-attachments/assets/bbc7222c-0740-4f2d-af6f-a8220686668e

<br/>

---

<br/>

## 제출 버튼 클릭시 

https://github.com/user-attachments/assets/bf52f8c6-f07b-4e4b-9685-6493ed874a5c

```javascript

$(document).on('click', '#ex-sub-btn', function () {

    // 응시 시험 정보들
    let examCode = $('#examCode').val();
    let year = $('#year').val();
    let type = $('#type').val();
    let electiveSubjects = $('#elesubs').val();
    let requiredSubjects = $('#reqsubs').val();
    let memberCode = $('#memberCode').val();

    // 선택되어있는 보기 data 속성을 읽고 '과목코드_문제번호_선택보기번호' 형태의 문자열로 만들어 배열로 저장
    // ex) SE_7_1 -> 스포츠교육학 7번문제 1번 보기 선택
    let selectedElements = document.querySelectorAll('[data-selected="1"]');
    let form = Array.from(selectedElements).map(el => el.getAttribute('data-subjectcode')+'_'+el.getAttribute('data-questionno')+'_'+el.getAttribute('data-optionno'));

    if(!confirm('정말 제출하시겠습니까? 마지막으로 한 번 더 확인하세요!')){
        return;
    }

    // 요청 body 구성
    let formData = {
        examCode : examCode,
        year : year,
        type : type,
        electiveSubjects : electiveSubjects,
        requiredSubjects : requiredSubjects,
        memberCode: memberCode,
        form : form
    };

    // 서버에 채점 요청
    $.ajax({
        url: '/exam/submit', // 서버 URL
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(formData),
        success: function(response) {

            if(response['err']){
                alert("서버 에러입니다. 화면을 떠나지 마세요 응시 기록은 유지됩니다. 잠시 후에 다시 제출해주세요. ");
                return;
            }

            let scoreList = response['subScore'];
            let detailList = response['detScore'];

            // 동적으로 채점 결과지를 생성하고 모달을 띄워줍니다.
            let j = 0;
            let apstr = '';
            for (let i = 0; i < detailList.length; i++) {

                if(i === 0){
                    j = 111;
                }else if(i === 20){
                    j = 222;
                    apstr = '';
                }else if(i === 40){
                    j = 333;
                    apstr = '';
                }else if(i === 60){
                    j = 444;
                    apstr = '';
                }else if(i === 80){
                    j = 555;
                    apstr = '';
                }else if(i === 100){
                    j = 666;
                    apstr = '';
                }else if(i === 120){
                    j = 777;
                    apstr = '';
                }else if(i === 140){
                    j = 888;
                    apstr = '';
                }


                let sc = detailList[i];
                if(sc.result == 'X'){
                    apstr = apstr + '<span class="failure-sub">'+sc.myAnswerNo+'</span>'
                }else{
                    apstr = apstr + '<span class="success-sub">'+sc.myAnswerNo+'</span>'
                }

                if(i === 19){
                    $('#ot'+j).append(apstr);
                }else if(i === 39){
                    $('#ot'+j).append(apstr);
                }else if(i === 59){
                    $('#ot'+j).append(apstr);
                }else if(i === 79){
                    $('#ot'+j).append(apstr);
                }else if(i === 99){
                    $('#ot'+j).append(apstr);
                }else if(i === 119){
                    $('#ot'+j).append(apstr);
                }else if(i === 139){
                    $('#ot'+j).append(apstr);
                }else if(i === 159){
                    $('#ot'+j).append(apstr);
                }

            }

            let tar = $('#score-t-ar');
            let sar = $('#score-s-ar');
            let k = 0;
            let mytscore = 0;
            let subres = 'P';
            for (let i = 0; i < scoreList.length; i++) {
                const s = scoreList[i];

                if (i <= 3) {
                    if (s.result == 'F') {
                        tar.append(`<div class="sub-score"><span class="failure-sub">${s.subTitle}: ${s.score}점 / none pass</span></div>`);
                        subres = 'F';
                    } else {
                        tar.append(`<div class="sub-score"><span class="success-sub">${s.subTitle}: ${s.score}점 / pass</span></div>`);
                    }
                } else {
                    if (s.result == 'F') {
                        sar.append(`<div class="sub-score"><span class="failure-sub special">${s.subTitle}: ${s.score}점 / none pass</span></div>`);
                        subres = 'F';
                    } else {
                        sar.append(`<div class="sub-score"><span class="success-sub special">${s.subTitle}: ${s.score}점 / pass</span></div>`);
                    }
                }
                mytscore += s.score;
                if(i === 0){
                    k= k+1;
                    $('#ot'+k+''+k).append('<span id="ot1">'+s.subTitle+'</span>').addClass('omr-title');
                }
                if(i !== 0){
                    if(s.subTitle != scoreList[i-1].subTitle){
                        k= k+1;
                    $('#ot'+k+''+k).append('<span id="ot1">'+s.subTitle+'</span>').addClass('omr-title');
                    }
                }
            }

            if(scoreList.length < 8){
                let k = 8 - scoreList.length;

                for(let i=1; i<=k; i++){
                    sar.append(`<div class="sub-score" style="border: none !important; background-color: #FFFFFF !important;"><span class="success-sub special" style="border: none !important; background-color: #FFFFFF !important;"></span></div>`);
                }

            }


            let tsc = scoreList.length * 100;

            let restr = '';

            if(mytscore >= (scoreList.length * 100)*0.6 && subres == 'P'){
                restr = '<span style="color: #0078C4 !important">총점: '+mytscore+' / '+tsc+'</span> <span class="success-sub" style="font-size: 13px; background-color: #FFFFFF !important;"> 결과 : pass </span>';
            } else if(mytscore < (scoreList.length * 100)*0.6 || subres == 'F'){
                restr = '<span style="color: #0078C4 !important">총점: '+mytscore+' / '+tsc+'</span> <span class="failure-sub" style="font-size: 13px; background-color: #FFFFFF !important;"> 결과 : none pass </span>';
            }



            $('#total-score').prepend(restr)


            $('.score-modal-overlay').attr('style','display: flex;');


        },
        error: function(xhr, status, error) {
            console.error('Error:', error);
            alert('서버 에러입니다. 잠시 후 다시 시도해주세요.');
        }
    });
    return;
});

```

### back-end code

- ajax 요청을 수신하여 처리합니다.
- 1차적으로 문자열 형태의 응시 데이터를 list로 변환해줍니다.

```java

    @PostMapping("/submit")
    @ResponseBody
    public Map<String , List<Score>> testSubmit (@RequestBody Map<String,Object> form, ServletRequest request,Model model){
        Map<String , List<Score>> score = new HashMap<>();
            try{
            String examCode = (String)form.get("examCode") == null ? "" : (String)form.get("examCode");
            String year = (String)form.get("year") == null ? "" : (String)form.get("year");
            String type = (String)form.get("type") == null ? "" : (String)form.get("type");
            String electiveSubjects = (String)form.get("electiveSubjects") == null ? "" : (String)form.get("electiveSubjects");
            String requiredSubjects = (String)form.get("requiredSubjects") == null ? "" : (String)form.get("requiredSubjects");
            String memberCode = (String)form.get("memberCode") == null ? "" : (String)form.get("memberCode");
            List<String> myAnswer = (List<String>)form.get("form") == null ? new ArrayList<>() : (List<String>)form.get("form");


            // 과목코드/과목코드/.. 응시 과목 문자열을 list 로 변환
            List<String> eleList = Arrays.stream(electiveSubjects.split("/"))
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());
            List<String> reqList = Arrays.stream(requiredSubjects.split("/"))
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());

            // 섞여있는 응시 정보를 과목순 , 문제번호순 으로 정렬
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
            // 프론트에서 1차적으로 처리가 되었지만 서버에서 한 번 더 확인해줍니다.
            for(String myanswer : myAnswer){
               String a = myanswer.split("_")[0];
               if(myAnswerList.get(a) != null){
                   myAnswerList.get(a).add(myanswer);
               }
            }

            
            return testService.getScore(examCode,type,year,eleList,reqList,myAnswerList , memberCode);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("endpoint---------------------------/exam/submit",e);
            List<Score> errs = new ArrayList<>();
            Score s = new Score();
            s.setErr("err");
            score.put("err" , errs);
            return score;
        }

    }

```



### back-end code

- 실제 채점 로직

```java

// Score 객체
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



 @Transactional
    public Map<String , List<Score>> getScore(String examCode , String type , String year , List<String> eleList , List<String> reqList , Map<String , List<String>> myAnswerList , String memberCode) throws JsonProcessingException {

        // 채점 결과 (과목별 점수)
        List<Score> result = new ArrayList<>();

        // 답안을 저장할 Map
        Map<String , List<AnswerDetail>> answerList = new HashMap<>();
        for(String sub : eleList){
            answerList.put(sub , new ArrayList<>());
        }
        for(String sub : reqList){
            answerList.put(sub , new ArrayList<>());
        }

        // 응시 정보 상세 (상세 응시정보)
        List<Score> tsl = new ArrayList<>();
        int total = 0;
        boolean isPassed = true;
        for(String subCode : answerList.keySet()){
    
            AnswerMaster form = new AnswerMaster();
            form.setType(type);
            form.setYear(year);
            form.setExamSubjectCode("%"+examCode+"_"+subCode+"/%");

            // answer_master 테이블의 answer_detail 칼럼에 json 형태로 저장되어있음
/*
    [{"answerDetailCode":0,"answerMasterCode":0,"questionNo":1,"answerNo":4,"point":5},{"answerDetailCode":0,"answerMasterCode":0,"questionNo":2,"answerNo":2,"point":5},
    ..
    ..
    ..
    {"answerDetailCode":0,"answerMasterCode":0,"questionNo":17,"answerNo":2,"point":5},{"answerDetailCode":0,"answerMasterCode":0,"questionNo":18,"answerNo":3,"point":5},
    {"answerDetailCode":0,"answerMasterCode":0,"questionNo":19,"answerNo":2,"point":5},{"answerDetailCode":0,"answerMasterCode":0,"questionNo":20,"answerNo":3,"point":5}]
*/
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

            Subject sub = sql.selectOne("com.spocbt.spocbt.mapper.SubjectMapper.findBySubjectCode",subCode);

            // for 문을 순회하며 채점 시행
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
                        }else {
                            sc.setResult("X");
                        }
                        break;
                    }

                }
                tsl.add(sc);
            }


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

        }

        Map<String , List<Score>> lr = new HashMap<>();
        lr.put("subScore" , result);
        lr.put("detScore",tsl);

        return lr;
    }

```












