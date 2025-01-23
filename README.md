# 생활스포츠지도사 기출문제 온라인 풀이 사이트.
<http://spocbt.cafe24.com/exam/list>

## 개발 계기

'생활스포츠지도사'자격증은 체육 대학을 졸업하기 위해 필수로 취득해야 하는 자격증 중 하나입니다.

필기와 구술/실기로 이루어진 이 자격증은 전공생들에게는 그리 어렵지 않은 수준의 필기 난이도 이며, 어느정도 경력이 되는 생활체육인들도 많이들 취득하고 있습니다.
1-2달 전부터 기출문제를 위주로 공부하고 시험에 임하는 것이 보통입니다.

저 또한 그렇게 자격증을 준비하여 취득하였습니다.
당시 시험을 준비하며 느낀 점은 1년에 한 번 있는 시험이지만 응시자 수가 적지 않다는 점이었고.(4~5만명)
그에 반해 관련 정보를 얻을 수 있는 기반이 적은 느낌이었습니다.

이에 저는 온라인으로 기출문제를 풀 수 있는 기능을 바탕으로 회원을 모집한 뒤 이를 통해 수익을 이끌어 낼 수 없을까 하는 생각이 들었습니다.

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

그렇게 24/12/08 ~ 25/01/23 약 5주간 틈틈이 개발을 완료하고 서버 배포에 성공했습니다.

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

```

import os
from bs4 import BeautifulSoup

def process_html_files(input_dir, output_dir):
    """
    span 태그에서 ①,②,③,④를 검색하여
    - 하나만 포함된 경우: data-answerno="1", "2", "3", "4" 추가
    - 둘 이상 포함된 경우: data-answerno="duplicate" 추가
    처리 후 통계를 TXT 파일로 저장
    """
    if not os.path.exists(output_dir):
        os.makedirs(output_dir)  # 출력 폴더 생성

    answer_numbers = {'①': '1', '②': '2', '③': '3', '④': '4'}
    summary_lines = []

    for file_name in os.listdir(input_dir):
        if file_name.endswith(".html"):
            input_path = os.path.join(input_dir, file_name)
            output_path = os.path.join(output_dir, file_name)

            with open(input_path, "r", encoding="utf-8") as file:
                soup = BeautifulSoup(file, "html.parser")

            # 카운트 초기화
            total_count = 0
            count_1 = 0
            count_2 = 0
            count_3 = 0
            count_4 = 0
            count_duplicate = 0

            for span in soup.find_all("span"):
                if span.string:
                    text = span.string
                    matches = [answer_numbers[char] for char in text if char in answer_numbers]

                    if len(matches) == 1:
                        span["data-answerno"] = matches[0]
                        if matches[0] == '1':
                            count_1 += 1
                        elif matches[0] == '2':
                            count_2 += 1
                        elif matches[0] == '3':
                            count_3 += 1
                        elif matches[0] == '4':
                            count_4 += 1
                    elif len(matches) > 1:
                        span["data-answerno"] = "duplicate"
                        count_duplicate += 1

            total_count = count_1 + count_2 + count_3 + count_4 + count_duplicate

            # HTML 파일 저장
            with open(output_path, "w", encoding="utf-8") as file:
                file.write(str(soup))

            # 결과를 summary_lines에 추가
            summary_line = (f"{file_name} / totalCnt: {total_count} / 1cnt: {count_1} / 2cnt: {count_2} "
                            f"/ 3cnt: {count_3} / 4cnt: {count_4} / duplicateCnt: {count_duplicate}")
            summary_lines.append(summary_line)

            print(f"Processed: {file_name}")

    # TXT 파일 생성
    summary_path = os.path.join(output_dir, "summary.txt")
    with open(summary_path, "w", encoding="utf-8") as summary_file:
        summary_file.write("\n".join(summary_lines))

    print(f"Summary file created at: {summary_path}")

# 경로 설정
input_dir = r"C:\Users\jeon\Desktop\온라인 자격증 시험\html변환\4. hidden 제거 [ html ]"
output_dir = r"C:\Users\jeon\Desktop\온라인 자격증 시험\html변환\5. 보기 번호 data 부여 [ html ]"

process_html_files(input_dir, output_dir)


```



![화면 캡처 2025-01-23 175213](https://github.com/user-attachments/assets/2ee6e0cd-49fb-4951-9622-7f075585e36c)
![화면 캡처 2025-01-23 175246](https://github.com/user-attachments/assets/1ac25512-a49d-45e0-9bba-cbd431bf88ff)







