import React, { useState , useEffect }  from 'react';
import axios from 'axios'
export default function Header( props ) {
    // let [ login , setLogin] = useState( JSON.parse( localStorage.getItem("login_token") ) );
    let [ login , setLogin] = useState( null );
    // 로그아웃
    const logOut = ()=>{
        sessionStorage.setItem("login_token" , null );
        setLogin( null );
        axios.get("/member/logout").then( r=>{ console.log(r); });  // 백엔드의 인증세션 지우기
    }

    useEffect( ()=>{
            axios
                .get("/member/info"  )
                .then( r => {
                    console.log( r.data )
                    if( r.data != '' ){
                       sessionStorage.setItem("login_token" , JSON.stringify( r.data ) );
                       setLogin( JSON.parse( sessionStorage.getItem("login_token") )  );
                    }
                })
    },[])


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