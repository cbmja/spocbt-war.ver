$(document).ready(function () {
            // 높이 자동 조정 함수
            function adjustHeight($textarea) {
                $textarea.height('auto'); // 기존 높이를 초기화
                $textarea.height($textarea[0].scrollHeight); // 내용에 맞게 높이 설정
            }

            // 모든 .comment-ele 요소에 대해 초기 높이 설정
            $('.comment-ele').each(function () {
                adjustHeight($(this));
            });

            // 입력 이벤트 (readonly라 필요 없을 수 있음)
            $('.comment-ele').on('input', function () {
                adjustHeight($(this));
            });
        });

 // 25-01-14 - 1차 ok
$(document).on('click', '.comment-delete', function(){ // 25-01-14 - 1차 ok

    let commentSeq = $(this).data("commentseq");

    if(confirm("정말 삭제하시겠습니까?")){


        $.ajax({
            url: '/board/comment/delete', // 서버 URL
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({commentSeq: commentSeq}),
            success: function(response) {
                console.log(response);
                if(response['response'] == 'success'){
                    alert('삭제완료!');

                    $('.comment-list-ar').empty();
                    let str = response['appendStr'];
                    $('.comment-list-ar').append(str);

                    function adjustHeight($textarea) {
                        $textarea.height('auto'); // 기존 높이를 초기화
                        $textarea.height($textarea[0].scrollHeight); // 내용에 맞게 높이 설정
                    }

                    // 새롭게 추가된 .comment-ele 요소에 대해 높이 설정
                    $('.comment-ele').each(function() {
                        adjustHeight($(this));
                    });

                    return;
                }else if(response['response'] == 'notMine'){
                    alert('내가 작성한 글이 아닙니다.');
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

    }

});

 // 25-01-14 - 1차 ok
$(document).on('click', '.comment-submit-btn', function(){ // 25-01-14 - 1차 ok

    let content = $("#comment-content").val();
    let boardSeq = $("#board-seq").val();
    let isLogin = $("#is-login").val();

    if(isLogin != '1'){
        alert('로그인 해주세요.');
        return;
    }

    if(content.trim() == '' || !content){
        alert('댓글 내용을 입력해주세요.');
        return;
    }
    if(!boardSeq){
        alert('서버 에러입니다.');
        return;
    }

    content = content.trim();


    $.ajax({
        url: '/board/comment/submit', // 서버 URL
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({content: content , boardSeq: boardSeq}),
        success: function(response) {
            console.log(response);
            if(response['response'] == 'success'){
                $('#comment-content').val('');
                alert('작성완료!');

                $('.comment-list-ar').empty();
                let str = response['appendStr'];
                $('.comment-list-ar').append(str);

                function adjustHeight($textarea) {
                    $textarea.height('auto'); // 기존 높이를 초기화
                    $textarea.height($textarea[0].scrollHeight); // 내용에 맞게 높이 설정
                }

                // 새롭게 추가된 .comment-ele 요소에 대해 높이 설정
                $('.comment-ele').each(function() {
                    adjustHeight($(this));
                });

                return;
            }else if(response['response'] == 'dup'){
                alert('이미 존재하는 댓글입니다.');
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
$(document).on('click', '.board-delete', function(){ // 25-01-14 - 1차 ok

    let boardSeq = $(this).data("boardseq");
    let boardType = $("#board-type").val();

    if(confirm("정말 삭제하시겠습니까?")){


        $.ajax({
            url: '/board/delete', // 서버 URL
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({boardSeq: boardSeq}),
            success: function(response) {
                console.log(response);
                if(response == 'success'){
                    alert('삭제완료!');

                    window.location.href='/board/list?type='+boardType;

                    return;
                }else if(response == 'notMine'){
                    alert('내가 작성한 글이 아닙니다.');
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

    }

});

 // 25-01-14 - 1차 ok
$(document).on('click', '.board-edit', function(){ // 25-01-14 - 1차 ok

    let boardSeq = $(this).data("boardseq");

    window.location.href='/board/edit?boardSeq='+boardSeq;

});

 // 25-01-14 - 1차 ok
$(document).on('click', '.comment-edit', function(e){ // 25-01-14 - 1차 ok

    let commentSeq = $(this).data('commentseq');

    $('#'+commentSeq).removeAttr('readonly');

    $('#'+commentSeq).attr("style" , "background-color: #FFFFFF !important;");

    $('#'+commentSeq+'-i-r').empty();

    $('#'+commentSeq+'-i-r').append('<span class="comment-edit-submit" data-commentseq="'+commentSeq+'">저장</span>');

});

 // 25-01-14 - 1차 ok
$(document).on('click', '.comment-edit-submit', function(){ // 25-01-14 - 1차 ok

    let commentSeq = $(this).data("commentseq");
    let content = $('#'+commentSeq).val();

            $.ajax({
                url: '/board/comment/edit', // 서버 URL
                method: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({commentSeq: commentSeq , content :content}),
                success: function(response) {
                    console.log(response);
                    if(response == 'success'){
                        alert('수정완료!');
                            $('#'+commentSeq+'-i-r').empty();
                            $('#'+commentSeq+'-i-r').append('<span class="comment-edit" data-commentseq="'+commentSeq+'">수정</span>'
                                                     +'<span>&nbsp;&nbsp;|&nbsp;&nbsp;</span>'
                                                     +'<span class="comment-delete" data-commentseq="'+commentSeq+'">삭제</span>');

                        $('#'+commentSeq).attr('readonly', true);
                        $('#'+commentSeq).attr("style" , "background-color: #F6F6F6 !important;");
                        return;
                    }else if(response == 'notMine'){
                        alert('내가 작성한 댓글이 아닙니다.');
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