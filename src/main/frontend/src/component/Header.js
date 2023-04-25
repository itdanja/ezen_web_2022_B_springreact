import React, { useState , useEffect }  from 'react';
import axios from 'axios'
export default function Header( props ) {
    // let [ login , setLogin] = useState( JSON.parse( localStorage.getItem("login_token") ) );

    let [ login , setLogin] = useState( null ); // 로그인 상태

    // 로그아웃
    const logOut = ()=>{
        sessionStorage.setItem("login_token" , null ); // JS 세션 스토리지 초기화
        axios.get("/member/logout").then( r=>{ console.log(r); });  // 백엔드의 인증세션 지우기
        setLogin( null ); // 렌더링
        // window.location.href="/member/login";
    }
    // 로그인 상태 호출
    useEffect( () => {
        axios.get("/member/info")
            .then( r => { console.log(r.data );
                if( r.data != '' ){ // 로그인되어 있으면 // 서비스에서 null 이면 js에서 '' 이다 .
                    // JS 로컬 스토리지 에 저장
                    sessionStorage.setItem( "login_token" , JSON.stringify( r.data ) );
                    // 상태변수에 로컬 스토리지를 호출해서 상태변수에 데이터 저장 [ 렌더링 하기 위해 ]
                    setLogin( JSON.parse( sessionStorage.getItem("login_token") ) );
                }
            })
    },[] )

    return (
        <div>
            <a href="/"> Home </a>
            <a href="/board/list"> 게시판 </a>
            <a href="/admin/dashboard"> 관리자 </a>
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