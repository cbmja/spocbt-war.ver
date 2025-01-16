$(document).on('click', '.exam-element', function(e){

    let year = $(this).data('year');
    let title = $(this).data('title');
    let isupdate = $(this).data('isupdate');

    if(isupdate == '0'){
        alert('죄송합니다. ['+year+'년도 '+title+']모의시험은 현재 준비중입니다.');
        return;
    }

    let examCode = $('#examCode').val();


    $('.modal-title-s').remove();
    $('.modal-title').append('<span class="modal-title-s" id="modal-title1"data-year="'+year+'" data-examcode="'+examCode+'">'+year+'년도 '+title+'</span>');

    let eleb = $('.elective-background').removeClass('selected-subject').attr('data-isselected','0');


    eleb.find('.sub-type-badge').removeAttr('style').removeClass('selected-badge').addClass('elective-badge');
    eleb.find('.sub').removeAttr('style');


    $('.modal-overlay').css('display', 'flex');
    return;
});

$(document).on('click', '#closeModal', function(){
    $('.modal-overlay').css('display', 'none');
    return;
});

$(document).on('click', '.modal-overlay', function(e) {
    if ($(e.target).is('.modal-overlay')) {
        $('.modal-overlay').css('display', 'none');
    }
    return;
});

$(document).on('change', '#isAble', function() {

    if($(this).prop('checked')){
        $('.exam-element').each(function() {
            if ($(this).data('isupdate') == '0') {
                $(this).hide();
            }
        });
    }else{
        $('.exam-element').each(function() {
            if ($(this).data('isupdate') == '0') {
                $(this).show();
            }
        });
    }

    return;
});

$(document).on('change', '#examCode, input[name="examSort"]', function() {

    executeExamLogic();
    $('#isAble').prop('checked',false);

    return;
});
function executeExamLogic() {

    let sort = $('input[name="examSort"]:checked').val() || 'DESC';

    let examCode = $('#examCode').val();
        $.ajax({
            url: '/exam/list',
            method: 'GET',
            contentType: 'application/json',
            data: { examCode: examCode , sort: sort},
            success: function(response) {
                $('.exam-element').remove(); // 기존 목록 삭제
                $('.exam-prepare').remove();
                $('#eleCnt').remove();
                $('.modal-overlay').remove();
                $('.era').remove();

                const pattern = /<!--element ssss-->([\s\S]*?)<!--element eeee-->/;
                const match = response.match(pattern);
                const newelements = match[1].trim();

                let appendedElements = $(newelements).appendTo('.examList-list'); // 새 목록 삽입

                let HTA = appendedElements.find('#HTA');/*건체평*/
                let HE = appendedElements.find('#HE');/*건교*/
                let FA = appendedElements.find('#FA');/*기능해부*/
                let SPA = appendedElements.find('#SPA');/*노체*/
                let PA = appendedElements.find('#PA');/*병태*/
                let SE = appendedElements.find('#SE');/*스교*/
                let SS = appendedElements.find('#SS');/*사회*/
                let SP = appendedElements.find('#SP');/*심리*/
                let SN = appendedElements.find('#SN');/*영양*/
                let SETH = appendedElements.find('#SETH');/*윤리*/
                let ELT = appendedElements.find('#ELT');/*운부검*/
                let IE = appendedElements.find('#IE');/*상해*/
                let EP = appendedElements.find('#EP');/*생리*/
                let EB = appendedElements.find('#EB');/*역학*/
                let EPD = appendedElements.find('#EPD');/*처방*/
                let PAE = appendedElements.find('#PAE');/*유아*/
                let APS = appendedElements.find('#APS');/*장애*/
                let PME = appendedElements.find('#PME');/*체측평*/
                let TRN = appendedElements.find('#TRN');/*트레이닝*/
                let SPE = appendedElements.find('#SPE');/*특체*/
                let KHS = appendedElements.find('#KHS');/*체육사*/

                appendedElements.find('.table-ex-sub').removeClass('elective-background');
                appendedElements.find('.table-ex-sub').removeClass('required-background');
                appendedElements.find('.required-badge').remove();
                appendedElements.find('.elective-badge').remove();




                let elebadge = '<span class="sub-type-badge elective-badge">선택</span>';
                let reqbadge = '<span class="sub-type-badge required-badge">필수</span>';

                switch(examCode){
                    case 'PSI_2': /*2급 전문스포츠지도사*/
                    case 'LSI_2': /*2급 생활스포츠지도사*/
                        SE.addClass('elective-background');
                        SE.append(elebadge);
                        SS.addClass('elective-background');
                        SS.append(elebadge);
                        SP.addClass('elective-background');
                        SP.append(elebadge);
                        SETH.addClass('elective-background');
                        SETH.append(elebadge);
                        EP.addClass('elective-background');
                        EP.append(elebadge);
                        EB.addClass('elective-background');
                        EB.append(elebadge);
                        KHS.addClass('elective-background');
                        KHS.append(elebadge);
                        break;

                    case 'ASI_2': /*2급 장애인스포츠지도사*/
                        SE.addClass('elective-background');
                        SE.append(elebadge);
                        SS.addClass('elective-background');
                        SS.append(elebadge);
                        SP.addClass('elective-background');
                        SP.append(elebadge);
                        SETH.addClass('elective-background');
                        SETH.append(elebadge);
                        EP.addClass('elective-background');
                        EP.append(elebadge);
                        EB.addClass('elective-background');
                        EB.append(elebadge);
                        KHS.addClass('elective-background');
                        KHS.append(elebadge);

                        SPE.addClass('required-background');
                        SPE.append(reqbadge);
                        break;

                    case 'YSI': /*유소년스포츠지도사*/
                        SE.addClass('elective-background');
                        SE.append(elebadge);
                        SS.addClass('elective-background');
                        SS.append(elebadge);
                        SP.addClass('elective-background');
                        SP.append(elebadge);
                        SETH.addClass('elective-background');
                        SETH.append(elebadge);
                        EP.addClass('elective-background');
                        EP.append(elebadge);
                        EB.addClass('elective-background');
                        EB.append(elebadge);
                        KHS.addClass('elective-background');
                        KHS.append(elebadge);

                        PAE.addClass('required-background');
                        PAE.append(reqbadge);
                        break;

                    case 'SSI': /*노인스포츠지도사*/
                        SE.addClass('elective-background');
                        SE.append(elebadge);
                        SS.addClass('elective-background');
                        SS.append(elebadge);
                        SP.addClass('elective-background');
                        SP.append(elebadge);
                        SETH.addClass('elective-background');
                        SETH.append(elebadge);
                        EP.addClass('elective-background');
                        EP.append(elebadge);
                        EB.addClass('elective-background');
                        EB.append(elebadge);
                        KHS.addClass('elective-background');
                        KHS.append(elebadge);

                        SPA.addClass('required-background');
                        SPA.append(reqbadge);
                        break;

                    case 'HEM': /*건강운동관리사*/
                        HTA.addClass('required-background');
                        HTA.append(reqbadge);
                        FA.addClass('required-background');
                        FA.append(reqbadge);
                        PA.addClass('required-background');
                        PA.append(reqbadge);
                        SP.addClass('required-background');
                        SP.append(reqbadge);
                        ELT.addClass('required-background');
                        ELT.append(reqbadge);
                        IE.addClass('required-background');
                        IE.append(reqbadge);
                        EP.addClass('required-background');
                        EP.append(reqbadge);
                        EPD.addClass('required-background');
                        EPD.append(reqbadge);
                        break;

                    case 'LSI_1': /*1급 생활스포츠지도사*/
                        HE.addClass('required-background');
                        HE.append(reqbadge);
                        IE.addClass('required-background');
                        IE.append(reqbadge);
                        PME.addClass('required-background');
                        PME.append(reqbadge);
                        TRN.addClass('required-background');
                        TRN.append(reqbadge);
                        break;

                    case 'ASI_1': /*1급 장애인스포츠지도사*/
                        IE.addClass('required-background');
                        IE.append(reqbadge);
                        APS.addClass('required-background');
                        APS.append(reqbadge);
                        PME.addClass('required-background');
                        PME.append(reqbadge);
                        TRN.addClass('required-background');
                        TRN.append(reqbadge);
                        break;

                    case 'PSI_1': /*1급 전문스포츠지도사*/
                        SN.addClass('required-background');
                        SN.append(reqbadge);
                        IE.addClass('required-background');
                        IE.append(reqbadge);
                        PME.addClass('required-background');
                        PME.append(reqbadge);
                        TRN.addClass('required-background');
                        TRN.append(reqbadge);
                        break;
                }
                return;
            },
            error: function(xhr, status, error) {
                console.error('Error:', error);
                alert('서버 에러입니다. 잠시 후 다시 시도해주세요.');
            }
        });
};


$(document).on('click', '.elective-background', function(){

    let subjectCode = $(this).attr('id');

    let subjectTd = $('#'+subjectCode);
    let eleCnt = $('#eleCnt').val();

    let selectedCount = $('.elective-background').filter(function() {
                                 return $(this).attr('data-isselected') === '1';
                             }).length;

    let isselected = subjectTd.attr('data-isselected');


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
        subjectTd.find('.sub-type-badge').removeAttr('style');
        subjectTd.find('.sub').removeAttr('style');

        subjectTd.find('.selected-badge').removeClass('selected-badge').addClass('elective-badge');
        subjectTd.removeClass('selected-subject');
        subjectTd.attr('data-isselected','0');

    }
return;


});

// 24-12-29 : ok--
$(document).on('click', '#exam-btn', function(e) {

    let isLogin = $('#isLogin').val();
    if(!isLogin || isLogin == '0'){
        alert('로그인이 필요한 서비스입니다.');
        return;
    }


    let year = $('#modal-title1').attr('data-year'); // year
    let type = $('input[name="examType"]:checked').val(); // type
    let examCode = $('#modal-title1').attr('data-examcode'); // examCode
    let eleCnt = $('#eleCnt').val();

    // 선택과목중 선택한 과목
    let eleIds = $('.selected-subject')
                .map(function() {
                    return this.id; // 각 요소의 id 반환
                })
                .get() // jQuery 객체를 일반 배열로 변환
                .filter(id => id) // id가 유효한 값만 필터링
                .join('/') + '/';

    let selectedEle = $('.selected-subject')
              .map(function() {
                return this.id; // 각 요소의 id 반환
              })
              .get() // jQuery 객체를 일반 배열로 변환
              .filter(id => id);

    // 필수과목
    let reqIds = $('.required-background')
                    .map(function() {
                        return this.id; // 각 요소의 id 반환
                    })
                    .get() // jQuery 객체를 일반 배열로 변환
                    .filter(id => id) // id가 유효한 값만 필터링
                    .join('/') + '/';

    if(eleCnt != '0'){
        if(eleCnt != selectedEle.length){
            alert('선택과목을 확인해주세요. '+eleCnt+'개를 선택해야 합니다.');
            return;
        }
    }

    let url = `/exam/test?year=${year}&type=${type}&examCode=${examCode}&eleSubs=${eleIds}&reqSubs=${reqIds}`;

    window.location.href = url;

    return;
});
