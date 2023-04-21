import React , { useState , useEffect } from 'react'
import axios from 'axios'
import styles from '../../css/member/login.css'

export default function Login( props ) {

    // 2. 로그인
    const onLogin = ( ) => { console.log( "onLogin open" )
        let loginForm = document.querySelectorAll('.loginForm')[0];
        let loginFormData = new FormData(loginForm);

        axios
            .post("http://localhost:8080/member/login" , loginFormData )
            .then( r => {
                if( r.data == false ){
                    alert("동일한 회원정보가 없습니다. ");
                }else{
                    alert("로그인성공");
                    // JS 로컬 스토리지[브라우저 모두 닫혀도 사라지지 않는다. 도메인마다 따로 저장된다. ] 에 로그인 성공한 흔적 남기기
                    // localStorage.setItem("key",value); //  key·value : String 타입
                    // value 에 객체 대입시 [Object] ?????? 객체처럼 사용불가
                    // JSON.stringify( 객체 ) : 해당 객체를 String 타입의 json형식
                        // JSON.stringify( )    : Object --> String
                        // JSON.parse ( )       : String --> Object
                    localStorage.setItem("login_token" , JSON.stringify( r.data ) );

                    window.location.href="/";
                }
            })
    }

    return ( <>
            <h3> 로그인 페이지 </h3>
            <form className="loginForm">
                아이디[이메일] : <input type="text" name="memail" /> <br/>
                비밀번호 : <input type="text" name="mpassword" />  <br/>
                <button onClick={ onLogin } type="button"> 로그인 </button>
                <a href="http://localhost:8080/oauth2/authorization/google"> 구글로그인 </a>
                <a href="http://localhost:8080/oauth2/authorization/kakao"> 카카오로그인 </a>
                <a href="http://localhost:8080/oauth2/authorization/naver"> 네이버로그인 </a>
            </form>
    </> )
}

