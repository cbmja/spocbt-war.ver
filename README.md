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

### 실제 과목 태그입니다.

```ruby

<div class="table-td table-ex-sub elective-background" id="SP"><span class="sub">스포츠심리학</span><span class="sub-type-badge elective-badge">선택</span></div>

```

### 선택과목을 클릭했을 경우 입니다.

```ruby

$(document).on('click', '.elective-background', function(){

    ** 선택한 과목의 과목 코드 **
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



    }else{ // 현재 선택한 과목이 선택되어 있었던 경우 -> 선택 해제 처리
        subjectTd.find('.sub-type-badge').removeAttr('style');
        subjectTd.find('.sub').removeAttr('style');

        subjectTd.find('.selected-badge').removeClass('selected-badge').addClass('elective-badge');
        subjectTd.removeClass('selected-subject');
        subjectTd.attr('data-isselected','0');

    }
    return;


});

```

















