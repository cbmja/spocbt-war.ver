$(document).on('click', '.history-detail-btn', function() {

    let testCode = $(this).data('testcode');
    console.log(testCode);
    let year = $(this).data('year');
    let examTitle = $(this).data('title');
    let type = $(this).data('type');
    $.ajax({
        url: '/exam/test/detail', // 서버 URL
        method: 'GET',
        data: { testCode: testCode }, // 수정된 부분
        success: function(response) {
            console.log('Response:', response);
            // 성공 시 추가 작업 수행
            if(response['err']){
                alert("서버 에러입니다. 잠시 후에 다시 시도하세요.");
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
                    $('#ot'+j).empty();
                    $('#ot'+j).append(apstr);
                }else if(i === 39){
                $('#ot'+j).empty();
                    $('#ot'+j).append(apstr);
                }else if(i === 59){
                $('#ot'+j).empty();
                    $('#ot'+j).append(apstr);
                }else if(i === 79){
                $('#ot'+j).empty();
                    $('#ot'+j).append(apstr);
                }else if(i === 99){$('#ot'+j).empty();
                    $('#ot'+j).append(apstr);
                }else if(i === 119){$('#ot'+j).empty();
                    $('#ot'+j).append(apstr);
                }else if(i === 139){$('#ot'+j).empty();
                    $('#ot'+j).append(apstr);
                }else if(i === 159){$('#ot'+j).empty();
                    $('#ot'+j).append(apstr);
                }

            }







            let tar = $('#score-t-ar');
            tar.empty();
            let sar = $('#score-s-ar');
            sar.empty();
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
                    $('#ot'+k+''+k).empty();
                    $('#ot'+k+''+k).append('<span id="ot1">'+s.subTitle+'</span>').addClass('omr-title');
                }
                if(i !== 0){
                    if(s.subTitle != scoreList[i-1].subTitle){
                        k= k+1;
                    //$('#ot'+k).remove();
                    $('#ot'+k+''+k).empty();
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


            $('#total-score').empty();
            $('#total-score').prepend(restr);

            $('#exma-info-ar').empty();

            $('#exma-info-ar').append(`<span>${year} 년도 ${examTitle} / ${type} 타입</span>`);

            $('.score-modal-overlay').attr('style','display: flex;');

            return;




        },
        error: function(xhr, status, error) {
            console.error('Error:', error);
            alert('서버 에러입니다. 잠시 후 다시 시도해주세요.');
        }
    });

});


$(document).on('click', '#close-detail', function() {

    $('.score-modal-overlay').attr('style','display: none;');
    return;

});


