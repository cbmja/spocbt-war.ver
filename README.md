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
















