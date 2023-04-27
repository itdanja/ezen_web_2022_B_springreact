import React,{ useState , useEffect } from 'react';
import axios from 'axios';
export default function ReplyList( props ) {
    const [ replyDtoList , setReplyDtoList ] = useState( props.replyList );
    const onWriteHandler = () =>{
        props.onReplyWrite( document.querySelector('.rcontent').value );
    }
    return (<>
        <input className="rcontent" type="text" />  <button onClick={onWriteHandler}> 댓글작성 </button>
        <h6> 댓글 목록 </h6>
    </>)
}