# 📖생활스포츠지도사 기출문제 온라인 풀이 사이트.
<br/>
### 배포: <http://spocbt.cafe24.com/exam/list>
<br/>
---
<br/>
## 프로젝트 소개 
- 온라인으로 기출문제를 풀고 채점해볼 수 있습니다.
- 내 시험 이력을 확인해 볼 수 있습니다.
- 커뮤니티를 통해 채육인들간의 자유로운 소통이 가능합니다.
<br/>
---
## 개발환경
- front : html5 , css3 , javascript , jquery , thymeleaf , jquery
- back : java , spring boot , mysql , mybatis
- util : github , fileZilla
<br/>
---
<br/>
---
## 개발인원
- 1명
---
<br/>
---
## 폴더 구조
<br/>

````
```
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
```
````

<br/>
---
<br/>

## 시험 목록
<br/>
### 메인 화면인 시험 목록 화면입니다.
<br/>
https://github.com/user-attachments/assets/3b33bf83-826e-4577-b9ec-2554831324e6
<br/>
---
<br/>
## 시험 선택 후 응시 과목 선택 화면
<br/>
### 시험 목록에서 응시할 시험을 선택하면 응시할 과목을 선택합니다.
<br/>
https://github.com/user-attachments/assets/28866aba-e48d-4db2-b7e1-6169a4a4713a
<br/>
---
<br/>
## 시험 응시 화면
<br/>
### 응시 과목윽 선택하면 응시할 시험지가 나타납니다.
<br/>
https://github.com/user-attachments/assets/7353434e-c158-4b60-b845-532141856f0c
<br/>
---
<br/>






