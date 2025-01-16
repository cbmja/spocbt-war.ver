 // 25-01-14 - 1차 ok
$(document).on('click', '.mypage-menu-ele', function(){ // 25-01-14 - 1차 ok

    let type = $(this).data("type");


    if(type == 'history'){
        window.location.href='/member/history';
    }else if(type == 'myBoard'){
        window.location.href='/member/myBoard';
    }




});