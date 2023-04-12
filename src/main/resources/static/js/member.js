
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
/*
    // 시큐리티 사용하므로 폼 전송으로 로그인 요청
    function onLogin(){
        let info = {
            memail : document.querySelector(".memail").value,
            mpassword : document.querySelector(".mpassword").value
        }
        $.ajax({
            url : "/member/login",
            method : "post" ,
            data : JSON.stringify(info) ,
            contentType : "application/json",
            success : (r)=>{
                console.log(r);
                if( r == true ){ alert('로그인이 되셨습니다.'); }
            }
        })
    }
*/
getMember();
function getMember(){
    $.ajax({
        url : "/member/info",
        method : "get",
        success : (r)=>{
            document.querySelector('.infobox').innerHTML = `${ r.mname }님`
            document.querySelector('.infobox').innerHTML +=
                `<button onclick="getLogout()" type="button">로그아웃</button>`
        }
    })
}

function getLogout(){
    $.ajax({
        url : "/member/logout",
        method : "get" ,
        success : (r)=>{
            location.href="/";
        }
    })
}









