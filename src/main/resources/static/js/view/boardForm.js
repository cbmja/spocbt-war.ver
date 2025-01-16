 // 25-01-14 - 1차 ok
$(document).on('click', '.b-submit-btn', function(){ // 25-01-14 - 1차 ok

    let memberCode = $("#memberCode").val();
    let title = $("#title").val();
    let content =  $("#content").val();
    let type = $(this).data('boardtype');
    const defaultText = `
    :::::::: 예시 ::::::::
    제목 예시 1 : 2019년도 2급 생활스포츠지도사 A형 스포츠사회학 18번 문제 질문
    제목 예시 2 : 운동생리학 심박수 질문
    처럼 제목에 질문하고자 하는 바를 적어주시면 빠른 답변을 받을 수 있습니다.
        `.trim();
    if(!memberCode || !type){
        alert('서버 에러입니다. 잠시 후 다시 시도해주세요.');
        return;
    }

    if(!title || title.trim() === ''){
        alert('제목을 입력하세요.');
        return;
    }

    if (title.length > 82) {
        alert("제목은 최대 81자까지 입력 가능합니다.");
        return;
    }

    if (content.trim() === '' || content.trim() === defaultText || !content) {
        event.preventDefault();
        alert('내용을 입력해주세요!');
        return;
    }



    let formData = {
        memberCode : memberCode,
        title : title,
        content : content,
        boardType : type
    };

    $.ajax({
        url: '/board/submit', // 서버 URL
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(formData),
        success: function(response) {

            if(response == 'ok'){
                alert('작성완료!');
                window.location.href='/board/list?type='+type;
                return;
            }else if(response == 'dupTitle'){
                alert('이미 존재하는 게시글 제목입니다.');
                return;
            }else{
                alert('서버 에러입니다. 잠시 후 다시 시도해주세요.');
                return;
            }


    },
    error: function(xhr, status, error) {
        console.error('Error:', error);
        alert('서버 에러입니다. 잠시 후 다시 시도해주세요.');
    }
    });


});

 // 25-01-14 - 1차 ok
$(document).on('click', '.b-edit-btn', function(){ // 25-01-14 - 1차 ok

    let memberCode = $("#memberCode").val();
    let title = $("#title").val();
    let content =  $("#content").val();
    let type = $(this).data('boardtype');
    let boardSeq = $(this).data('boardseq');
    const defaultText = `
    :::::::: 예시 ::::::::
    제목 예시 1 : 2019년도 2급 생활스포츠지도사 A형 스포츠사회학 18번 문제 질문
    제목 예시 2 : 운동생리학 심박수 질문
    처럼 제목에 질문하고자 하는 바를 적어주시면 빠른 답변을 받을 수 있습니다.
        `.trim();
    if(!memberCode || !type){
        alert('서버 에러입니다. 잠시 후 다시 시도해주세요.');
        return;
    }

    if(!title || title.trim() === ''){
        alert('제목을 입력하세요.');
        return;
    }

    if (title.length > 82) {
        alert("제목은 최대 81자까지 입력 가능합니다.");
        return;
    }

    if (content.trim() === '' || content.trim() === defaultText || !content) {
        event.preventDefault();
        alert('내용을 입력해주세요!');
        return;
    }



    let formData = {
        memberCode : memberCode,
        title : title,
        content : content,
        boardType : type,
        boardSeq : boardSeq
    };

    $.ajax({
        url: '/board/edit', // 서버 URL
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(formData),
        success: function(response) {

            if(response == 'success'){
                alert('수정완료!');
                window.location.href='/board/detail?seq='+boardSeq;
                return;
            }else{
                alert('서버 에러입니다. 잠시 후 다시 시도해주세요.');
                return;
            }


    },
    error: function(xhr, status, error) {
        console.error('Error:', error);
        alert('서버 에러입니다. 잠시 후 다시 시도해주세요.');
    }
    });


});


$(document).ready(function () {
    const $textarea = $('#content');

    $textarea.on('input', function () {
        $(this).css('height', 'auto'); // 높이를 초기화
        $(this).css('height', this.scrollHeight + 'px'); // 컨텐츠에 맞게 높이 설정
    });
});











