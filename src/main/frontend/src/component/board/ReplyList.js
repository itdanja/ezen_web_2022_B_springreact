import React,{ useState , useEffect } from 'react';
import axios from 'axios';
export default function ReplyList( props ) {
    // 1. 댓글 작성 핸들러
    const onWriteHandler = () =>{
        props.onReplyWrite( document.querySelector('.rcontent').value );
        document.querySelector('.rcontent').value = '';

    }
    // 2. 삭제 핸들러
    const onDeleteHandler = ( e , rno ) => {
        console.log( '삭제' + rno );
        props.onReplyDelete( rno ); // props 전달받은 삭제함수 실행
    }
    return (<>
        <input className="rcontent" type="text" />  <button onClick={onWriteHandler}> 댓글작성 </button>
        <h6> 댓글 목록 </h6>
        {
             props.replyList.map( (r)=>{
                return(<div>
                        <span> { r.rcontent } </span>
                        <span> { r.rdate } </span>
                        {
                            /* JSX 형식에서 함수에 매개변수 전달 */
                            /*  <마크업 이벤트 = { (e)=>{ 함수명( e , 매개변수 ) } } /> */
                        }
                        <button onClick={ (e)=>onDeleteHandler( e , r.rno ) } >삭제</button>
                    </div>)
            })
        }
    </>)
}