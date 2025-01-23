// 25-01-14 - 1차 ok
$(document).on('click', '.login-btn', function(){// 25-01-14 - 1차 ok

    let type = $(this).data('type');

    if(type == 'tester'){
        window.location.href='/member/tester';
        return;
    }else{
        window.location.href=`/member/auth?type=${type}`;
    }

});