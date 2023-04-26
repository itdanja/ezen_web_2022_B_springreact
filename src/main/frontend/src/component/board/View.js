import React,{ useState , useEffect } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom'; // HTTP 경로 상의 매개변수 호출 해주는 함수
export default function View( props ) {
   const params = useParams();

   const [ board , setBoard ] = useState( {} );
   useEffect( ()=>{
        axios.get("/board/getboard" , { params : { bno : params.bno }})
            .then( (r) => {
                console.log( r.data );
                setBoard( r.data );
            })
   } , [ ] )

   // 1. 현재 로그인된 회원이 들어왔으면
   const btnBox = () => {
        // 1.세션스토리지 확인해서 로그인 정보 보자
        let login = JSON.parse( sessionStorage.getItem("login_token") )
        console.log( login )
        console.log( login.mno )
        // 2. 현재 게시물의 작성자 회원번호 확인
        console.log( board.mno )
        {<div>
                  <button>삭제</button>
                  <button>수정</button>
        </div> }
   }

   return ( <>
        <div>
            <h3> 제목 </h3>
            <h3> 내용 </h3>
            { btnBox }
        </div>
   </>)
}
/*
   // useParams() 훅 : 경로[URL]상의 매개변수[객체] 반환
    // http://localhost:8080/board/view/26
    // http://localhost:8080/board/view/:bno    -----> useParams(); ----> { bno : 26 }
    // http://localhost:8080/board/view/26/안녕하세요
    // http://localhost:8080/board/view/:bno/:comment    -----> useParams(); ----> { bno : 26 , comment : 안녕하세요 }
*/