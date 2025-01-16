// 25-01-14 - ok
$(document).on('click', '.join-btn', function(){// 25-01-14 - ok

    let loginType = $("#loginType").val();
    let loginId = $("#loginId").val();
    let name = $("#name").val();
    let email = $("#email").val();
    let age = $('input[name="age"]:checked').val();
    let job = $('input[name="job"]:checked').val()

    console.log(loginType);
    console.log(loginId);
    console.log(name);
    console.log(email);
    console.log(age);
    console.log(job);

    let missingValue = '';
    if(!name){
        missingValue += '닉네임 , ';
    }
    if(!email){
        missingValue += '이메일 , ';
    }
    if(!age){
        missingValue += '연령 , ';
    }
    if(!job){
        missingValue += '직업 , ';
    }

    if(missingValue !== ''){
        let str = missingValue.slice(0, -2);
        alert(`${str} 값을 입력해주세요.`);
        return;
    }

    let check = $("#name").attr('data-check');

    if(check === 'noCheck'){
        alert('닉네임 중복확인을 해주세요.');
        return;
    }
    if(check == '0'){
        alert('이미 사용중인 닉네임 입니다.');
        return;
    }

    let formData = {
        loginType : loginType,
        loginId : loginId,
        name : name,
        email : email,
        age : age,
        job: job
    };

    $.ajax({
        url: '/member/join', // 서버 URL
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(formData),
        success: function(response) {

            if(response == "success"){
                alert('회원가입 성공. 환영합니다!');
                window.location.href = "/exam/list";
                return;
            }else{
                alert('서버 에러입니다. 잠시 후 다시 시도해주세요.');
            }



    },
    error: function(xhr, status, error) {
        console.error('Error:', error);
        alert('서버 에러입니다. 잠시 후 다시 시도해주세요.');
    }
    });

    return;
});

// 25-01-14 - ok
$(document).on('click', '.dup-check', function(){

    let name = $("#name").val();

    $.ajax({
        url: '/member/check', // 서버 URL
        method: 'GET',
        contentType: 'application/json',
        data: {name:name},
        success: function(response) {

            if(response == 'ok'){
                $("#name").attr('data-check' , '1');
                alert('사용 가능한 닉네임 입니다.');
                return;
            }else if(response == 'no'){
                $("#name").attr('data-check' , '0');
                alert('이미 사용중인 닉네임 입니다.');
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

$(document).on('change', '#name', function(){

    $("#name").attr('data-check' , 'noCheck');

});