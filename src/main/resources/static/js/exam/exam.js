
$(document).on('click', '.exam-h-hide', function(){
    $('.exam-header').css('display', 'none');
    $('body').css('margin-top', '0');
    $('.exam-h-show').css('display', 'block');

    $('#ex-sub-btn').css('top','40px');

    return;
});

$(document).on('click', '.exam-h-show', function(){
    $('.exam-header').css('display', 'block');
    $('body').css('margin-top', '210px');
    $('.exam-h-show').css('display', 'none');

    $('#ex-sub-btn').css('top','230px');

    return;
});

// 24-12-29 : ok--
// 보기 클릭
let eSub = $('#elesubs').val();
let rSub = $('#reqsubs').val();
$(document).on('click', '[data-optionno]', function () {

    let dataSub = $(this).data('subjectcode')+'/';

    if(!eSub.includes(dataSub) && !rSub.includes(dataSub)){
        alert('선택하지 않은 과목입니다.');
        return;
    }

    let isSelected = $(this).attr('data-selected');

    if(!isSelected || isSelected == '0'){
        $(this).addClass('selected-option');
        $(this).attr('data-selected', '1');
        console.log('선택');
    }else{
        $(this).removeClass('selected-option');
        $(this).attr('data-selected', '0');
        console.log('선택해제');
    }
    return;
});

// 24-12-29 : ok--
// 시험 제출
$(document).on('click', '#ex-sub-btn', function () {

    let examCode = $('#examCode').val();
    let year = $('#year').val();
    let type = $('#type').val();
    let electiveSubjects = $('#elesubs').val();
    let requiredSubjects = $('#reqsubs').val();
    let memberCode = $('#memberCode').val();

    let selectedElements = document.querySelectorAll('[data-selected="1"]'); // 선택되어있는 모든 보기 data
    let form = Array.from(selectedElements).map(el => el.getAttribute('data-subjectcode')+'_'+el.getAttribute('data-questionno')+'_'+el.getAttribute('data-optionno'));
    // 선택되어있는 보기 data 속성을 읽고 '과목코드_문제번호_선택보기번호' 형태의 문자열로 만들어 배열로 저장
    // ex) SE_7_1 -> 스포츠교육학 7번문제 1번 보기 선택

    if(!confirm('정말 제출하시겠습니까? 마지막으로 한 번 더 확인하세요!')){
        return;
    }

    let formData = {
        examCode : examCode,
        year : year,
        type : type,
        electiveSubjects : electiveSubjects,
        requiredSubjects : requiredSubjects,
        memberCode: memberCode,
        form : form
    };

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
                //console.log(sc.result);
                //console.log(sc.myAnswerNo);
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
                const s = scoreList[i]; // 현재 점수 객체

                if (i <= 3) {
                    // 0~3번째 인덱스까지의 처리
                    if (s.result == 'F') {
                        tar.append(`<div class="sub-score"><span class="failure-sub">${s.subTitle}: ${s.score}점 / none pass</span></div>`);
                        subres = 'F';
                    } else {
                        tar.append(`<div class="sub-score"><span class="success-sub">${s.subTitle}: ${s.score}점 / pass</span></div>`);
                    }
                } else {
                    // 4번째 인덱스 이후의 처리
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
                    //$('#ot'+k).remove();
                    $('#ot'+k+''+k).append('<span id="ot1">'+s.subTitle+'</span>').addClass('omr-title');
                }
                if(i !== 0){
                    if(s.subTitle != scoreList[i-1].subTitle){
                        k= k+1;
                    //$('#ot'+k).remove();
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

            if(mytscore >= (scoreList.length * 100)*0.6 && subres == 'P'){ // 합격
                restr = '<span style="color: #0078C4 !important">총점: '+mytscore+' / '+tsc+'</span> <span class="success-sub" style="font-size: 13px; background-color: #FFFFFF !important;"> 결과 : pass </span>';
            } else if(mytscore < (scoreList.length * 100)*0.6 || subres == 'F'){
                restr = '<span style="color: #0078C4 !important">총점: '+mytscore+' / '+tsc+'</span> <span class="failure-sub" style="font-size: 13px; background-color: #FFFFFF !important;"> 결과 : none pass </span>';
            }



            $('#total-score').prepend(restr)


            $('.score-modal-overlay').attr('style','display: flex;');

            //window.location.href = response;

        },
        error: function(xhr, status, error) {
            console.error('Error:', error);
            alert('서버 에러입니다. 잠시 후 다시 시도해주세요.');
        }
    });




    return;
});