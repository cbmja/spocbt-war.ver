$(document).on('scroll', function () {
    const $navMenu = $('.nav-menu'); // .nav-menu 선택자

    if ($(window).scrollTop() > 40) { // 스크롤 위치가 40px 이상일 때
        $navMenu.addClass('sticky');
    } else {
        $navMenu.removeClass('sticky');
    }
});


$(document).on('click', '.await-menu', function(){

    alert('준비중');
    return;
})
