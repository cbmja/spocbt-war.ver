$(document).on('click', '.write-btn', function(){

    let type = $('input[name="type"]:checked').val();
    window.location.href='/board/form?type='+type;

});

$(document).on('change', 'input[name="type"]', function(){

    let type = $('input[name="type"]:checked').val();
    window.location.href='/board/list?type='+type;

});


$(document).on('click', '#board-search-btn', function(){

    let searchType = $('#searchType').val();
    let search = $('#boardSearch').val();
    let type = $('input[name="type"]:checked').val();

    window.location.href='/board/list?type='+type+'&search='+search+'&searchType='+searchType;


});


$(document).on('click', '.board-element', function(){

    let seq = $(this).data('boardseq');

    window.location.href='/board/detail?seq='+seq;


});