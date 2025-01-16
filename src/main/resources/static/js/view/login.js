// 25-01-14 - 1차 ok
$(document).on('click', '.login-btn', function(){// 25-01-14 - 1차 ok

    let type = $(this).data('type');

    if(type == 'google' || type == 'sms'){
        alert('죄송합니다. 준비중입니다. 현재는 카카오, 네이버 로그인만 가능합니다.');
        return;
    }else{
        window.location.href=`/member/auth?type=${type}`;
    }

});