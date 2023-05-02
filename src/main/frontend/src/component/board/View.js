import React,{ useState , useEffect } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom'; // HTTP 경로 상의 매개변수 호출 해주는 함수

import ReplyList from './ReplyList';

import Container from '@mui/material/Container';

export default function View( props ) {
   const params = useParams(); // URL 쿼리스트링 변수요청
  // *. 현재 로그인된 회원이 들어왔으면
  const [ login , setLogin ] = useState( JSON.parse( sessionStorage.getItem('login_token') ) )
  const btnBox =
               login != null && login.mno == board.mno
               ? <div> <button onClick={ onDelete }>삭제</button>
                       <button onClick={ onUpdate }>수정</button> </div>
               : <div> </div>;

   // 게시물 : 게시물정보+댓글+대댓글
   const [ board , setBoard ] = useState( { // Restful api 으로 응답받은 게시물정보
        replyDtoList : []
   } );

    // 1. 현재 게시물 가져오는 axios 함수
    const getBoard = () => {
        axios.get("/board/getboard" , { params : { bno : params.bno }})
                .then( (r) => {
                    console.log( r.data );
                    setBoard( r.data );
                })
    }
    // 2. 컴포넌트 처음 열렸을때 getBoard 실행
    useEffect( ()=>{ getBoard(); } , [] );

    // 3. 게시물 삭제 함수
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
   // 4. 게시물 수정 페이지 이동 함수
   const onUpdate = () => { window.location.href="/board/update?bno="+board.bno }

   // 5. 댓글 작성시 랜더링
   const onReplyWrite = ( rcontent , rindex  ) =>{
           let info = {  rcontent : rcontent,  bno : board.bno , rindex : rindex }; console.log( info );
            axios.post("/board/reply" , info )
                    .then( (r)=>{
                       if( r.data == true ){
                            alert("댓글 작성 완료"); getBoard();
                       }else{   alert("로그인 후 가능 합니다. ");  }
                     });
    }

    // 6. 댓글 삭제  렌더링
    const onReplyDelete = ( rno ) =>{
        console.log( rno );
        axios.delete( "/board/reply" , { params: { "rno" : rno }})
            .then( r =>{
                if( r.data == true ){
                    alert("댓글 삭제 완료"); getBoard();
                }else{   alert("본인 댓글만 삭제할수 있습니다. ");  }
            })
    }
    // 7. 수정 렌더링
    const onReplyUpdate = ( rno , rcontent ) => {
        let info = { rno : rno , rcontent : rcontent  }
        axios.put( '/board/reply' , info )
            .then( r => {
                if( r.data == true ){
                    alert("댓글 수정 완료"); getBoard();
                }else{   alert("본인 댓글만 삭제할수 있습니다. ");  }
            })
    }
   return (
   <Container>
        <div>
            <h3> 제목 </h3> <h3> 내용 </h3>  { btnBox }
        </div>
        <ReplyList
            onReplyUpdate={ onReplyUpdate  }
            onReplyDelete={ onReplyDelete  }
            onReplyWrite={ onReplyWrite }
            replyList = { board.replyDtoList }
        />
   </Container>)
}
/*
   // useParams() 훅 : 경로[URL]상의 매개변수[객체] 반환
    // http://localhost:8080/board/view/26
    // http://localhost:8080/board/view/:bno    -----> useParams(); ----> { bno : 26 }
    // http://localhost:8080/board/view/26/안녕하세요
    // http://localhost:8080/board/view/:bno/:comment    -----> useParams(); ----> { bno : 26 , comment : 안녕하세요 }
*/