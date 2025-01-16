$(document).on('click', '.openKakao', function(){

    let url = $(this).data('url');
    window.open(url, '_blank');
})