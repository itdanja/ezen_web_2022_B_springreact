
function onSignup(){
    let info = {
        memail : document.querySelector(".memail").value,
        mpassword : document.querySelector(".mpassword").value,
        mname : document.querySelector(".mname").value,
        mphone : document.querySelector(".mphone").value
    }
    $.ajax({
        url : "/member/info",
        method : "post" ,
        data : JSON.stringify(info) ,
        contentType : "application/json",
        success : (r)=>{
            console.log(r);
            if( r == true  ){ alert('가입이 되셨습니다.')}
        }
    })
}

    // 시큐리티 사용하므로 폼 전송으로 로그인 요청
    function onLogin(){
        console.log('onLogin')
        let form = document.querySelectorAll(".loginForm")[0];
        console.log(form);
        let formdata = new FormData(form);
        console.log(formdata);
        let info = {
            memail : document.querySelector(".memail").value,
            mpassword : document.querySelector(".mpassword").value
        }
        console.log(info)
        $.ajax({
            url : "/member/login",
            method : "post" ,
            data : formdata ,
            contentType : false ,
            processData : false ,
            success : (r)=>{
                console.log(r);
                if( r == true ){ alert('로그인이 되셨습니다.'); }
            }
        })
    }

getMember();
function getMember(){
    $.ajax({
        url : "/member/info",
        method : "get",
        success : (r)=>{

            document.querySelector('.infobox').innerHTML = `${ r.mname }님`
            document.querySelector('.infobox').innerHTML +=
                `<a href="/member/logout"> <button>로그아웃</button> </a>`
        }
    })
}
/*
function getLogout(){
    $.ajax({
        url : "/member/logout",
        method : "get" ,
        success : (r)=>{
            location.href="/";
        }
    })
}
*/









