import React,{ useState , useEffect } from 'react';
import axios from 'axios';
export default function DashBoard( props ) {
    // 1.카테고리 등록 버튼을 눌렀을때 이벤트
    const setCategory = () => {    console.log('setCategory')
        let cname = document.querySelector(".cname");
        axios.post( '/board/category/write',{ "cname" : cname.value } )
            .then( (r) => { console.log(r)
                if( r.data == true ){ alert('카테고리 등록성공'); cname.value = '' }
            })
    }
    return(<>
        <h3> 관리자 페이지 </h3>
        <h6> 게시판 카테고리 추가 </h6>
        <input type="text" className="cname" />
        <button onClick={ setCategory } type="button"> 카테고리 등록</button>
    </>)
}