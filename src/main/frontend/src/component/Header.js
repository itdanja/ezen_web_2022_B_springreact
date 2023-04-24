import React, { useState }  from 'react';
import axios from 'axios'
export default function Header( props ) {
    // let [ login , setLogin] = useState( JSON.parse( localStorage.getItem("login_token") ) );
    let [ login , setLogin] = useState( JSON.parse( sessionStorage.getItem("login_token") ) );
    // 로그아웃
    const logOut = ()=>{
        sessionStorage.setItem("login_token" , null );
        axios.get("http://localhost:8080/member/logout").then( r=>{ console.log(r); });  // 백엔드의 인증세션 지우기
        window.location.href="/login";
    }
    return (
        <div>
            <a href="/"> Home </a>
            {
                login == null
                ? ( <>
                        <a href="/member/login"> 로그인 </a>
                        <a href="/member/signup"> 회원가입 </a>
                    </> )
                : ( <>
                        <button onClick={ logOut }>로그아웃</button>
                    </> )
            }
        </div>
    )
}