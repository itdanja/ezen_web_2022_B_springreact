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

    // 삭제 함수
     const onDelete = () =>{
           axios.delete("/board" , { params : { bno : params.bno }})
               .then( r => {
                   console.log( r.data );
                   if( r.data == true ){
                       alert('삭제 성공 ');
                       window.location.href="/board/list";
                   }else{ alert('삭제 실패')}
               })
      }
   // 수정 페이지 이동 함수
   const onUpdate = () => { window.location.href="/board/update?bno="+board.bno }

   const [ login , setLogin ] = useState( JSON.parse( sessionStorage.getItem('login_token') ) )
   // 1. 현재 로그인된 회원이 들어왔으면
   const btnBox =
                login != null && login.mno == board.mno
                ? <div> <button onClick={ onDelete }>삭제</button>
                        <button onClick={ onUpdate }>수정</button> </div>
                : <div> </div>
   return ( <>
        <div>
            <h3> 제목 </h3> <h3> 내용 </h3>  { btnBox }
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