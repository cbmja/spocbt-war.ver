# 생활스포츠지도사 기출문제 온라인 풀이 사이트.
<http://spocbt.cafe24.com/exam/list>
우측 상단의 tester 로그인으로 간편하게 확인해 보실 수 있습니다.
실제 로그인 회원가입도 가능


## 개발 계기

'생활스포츠지도사'자격증은 체육 대학을 졸업하기 위해 필수로 취득해야 하는 자격증 중 하나입니다.

필기와 구술/실기로 이루어진 이 자격증은 전공생들에게는 그리 어렵지 않은 수준의 필기 난이도 이며, 어느정도 경력이 되는 생활체육인들도 많이들 취득하고 있습니다.
1-2달 전부터 기출문제를 위주로 공부하고 시험에 임하는 것이 보통입니다.

저 또한 그렇게 자격증을 준비하여 취득하였습니다.
당시 시험을 준비하며 느낀 점은 1년에 한 번 있는 시험이지만 응시자 수가 적지 않다는 점이었고.(4~5만명)
그에 반해 관련 정보를 얻을 수 있는 기반이 적은 느낌이었습니다.

이에 저는 온라인으로 기출문제를 풀 수 있는 기능을 바탕으로 회원을 모집하여 구인/구직 , 커뮤니티 활동이 가능한 사이트를 만들고 싶었습니다.

---

## 일단 시작

이런저런 아이디어가 떠오를 때마다.
'와 이거 대박인데' , '이런거 만들면 진짜 대박이겠다' .. 
같은 상상을 하며 마치 금덩이라도 발견한 듯 가슴이 뛰었습니다. 물론 그런 설렘은 상상에 그쳤고 얼마 못 가 기억 너머로 사라지곤 했습니다.

그날따라 참 심심한 주말이었던 것으로 기억합니다. 뭔가에 홀린듯 침대에서 일어나 노트에 간단하게 화면을 그려보고 바로 개발을 시작했습니다.
특별한 결심이나 계기가 있었던 것은 아니었습니다. 상상에 그치고 사라졌던 여러 아이디어들과 다르지 않았던 이번 아이디어는 그렇게 시작되었습니다.

---

## 개발 과정

사실 직전 프로젝트에서 node.js / express 를 사용해보고 spring에 비해서 참 가볍다는 느낌을 받았기에 sciript 공부도 할 겸 node.js 를 사용할까도 고민했었지만,
결국은 익숙한 java / spring boot 를 선택했습니다. 이유는 mvp 를 빠르게 만들고 서버에 올리는 것을 최우선 목포로 설정했기 때문에 아직은 익숙하지 않은 node.js로 개발을 하게 된다면 시간이 조금 더 걸릴 것 같다고 생각했기 때문입니다.

그렇게 24/12/18 ~ 25/01/23 약 5주간 틈틈이 개발을 완료하고 서버 배포에 성공했습니다.

---

## 앞으로는?

일단 5월초에 필기시험이 예정되어 있기 때문에 2~3 월 중에 체육대학 sns계정을 통해 홍보를 요청할 예정입니다.
2,3,4 학년 체대 학생들은 보통 시험을 응시 하기때문에 홍보 결과가 어떻게 될지 궁금합니다.

일단은 500명 정도라도 가입을 해주면 좋겠지만 사실 기능이 그렇게 훌륭하지 않고 디자인 적인 부분도 많이 부족하기에 실제 사용자들이 많을 것 같지는 않습니다.

혼자 기획 / 디자인 / 개발 / 배포의 사이클을 겪어봤다는 부분에 의의를 두고 있습니다. 

---

## 에러사항

기출문제는 pdf로 제공이 되고 있었기에 해당 시험지를 통해 온라인으로 시험을 볼 수 있는방법을 구현하는 것이 문제였습니다.
문제 해결을 위해 'pdf to html' 을 구글링 해보니 이미 상용 솔루션들이 많이 존재했고 여러곳을 샘플링 해보았습니다. 
aspose 에서 제공하는 솔루션이 그중 제일 일관성 있게 변환이 되어 선택하게 되었습니다.

일단 기출문제를 모두 다운받은 후 html로 변환하였습니다.

다음으로는 선택지(보기) 를 클릭할 수 있도록 css 를 추가했습니다.
html 을 다루는 라이브러리를 서치하던 중 python의 BeautifulSoup 이라는 라이브러리가 있다는 것을 발견했습니다.
python 은 아예 다를 줄 모르지만 chat gpt 를 활용하여 기본적인 코드를 제공받고, 이후에 기본적인 조건문과 반복문만 필요한 부분으로 수정하여 사용했습니다.

'①' , '②' , '③' , '④' 가 포함된 <span>태그에 data-answerno 속성을 추가하고, css 를 통해 data-answerno 속성을 가진 태그에 hover 시 커서를 포인터로 변경 , 클릭시 배경색을 초록색으로 변경하는 기능을 추가했습니다.

이제 클릭된 상태의 선택자는 수집이 가능해 졌지만 어떤 과목의 몇 번 문제인지 구분할 수 있는 값이 필요했습니다. 이 또한 python을 통해 해결하였습니다.
<https://github.com/cbmja/spocbtPython.git>
해당레포지토리에 pdf 를 어떻게 html시험지로 변환했는지 상세한 코드가 있습니다.  

---

## 시연

### 시험 목록
![main](https://github.com/user-attachments/assets/1f3f2df1-2af8-4c8b-a0c9-61b4c8a60db4)


### 시험 선택 후 응시 과목 선택 화면
![exam_select](https://github.com/user-attachments/assets/75d5a1f4-4d92-4231-8cd8-debe7fe8645c)


### 시험 응시 화면
![test](https://github.com/user-attachments/assets/417eb289-9676-4017-9df5-254068789139)


### 시험 제출 후 채점 화면
![submit](https://github.com/user-attachments/assets/73077a56-aed1-4ead-afb3-5c22c265f158)


### 마이페이지 시험 이력 조회
![mypage](https://github.com/user-attachments/assets/90929a45-2368-4926-b16d-9e9e6ba3f292)


### 마이페이지 시험 이력 상세
![detail](https://github.com/user-attachments/assets/36e433ee-9311-47cd-bde1-3bfefca7be92)




