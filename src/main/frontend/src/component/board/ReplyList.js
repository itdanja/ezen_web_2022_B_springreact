import React,{ useState , useEffect } from 'react';
import axios from 'axios';
import styles from '../../css/board/board.css';

import ReplyInput from './ReplyInput';


export default function ReplyList( props ) {

    const [ replyList , setReplyList ] = useState([]);

    useEffect(()=>{ setReplyList( props.replyList )},[props.replyList])

    // 2. 삭제 핸들러
    const onDeleteHandler = ( e , rno ) => {
        console.log( '삭제' + rno );
        props.onReplyDelete( rno ); // props 전달받은 삭제함수 실행
    }

    const onRereplyHandler = ( e, rno ) => {
        replyList.forEach( (r , i )=>{
            if( r.rno == rno ){
                if( r.cusHTML == '' || r.cusHTML == undefined ) {
                    replyList[i].cusHTML =
                            <>
                                <ReplyInput onReplyWrite={ props.onReplyWrite } rindex={r.rno} />
                                        {
                                             r.rereReplyDtoList.map( (rr)=>{
                                                if( rr.readOnly == undefined ){ rr.readOnly = true; }
                                                return(<div className="rereplyBox">
                                                    <span className="replyMname"> { rr.mname } </span> <span className="replyRdate"> { rr.rdate } </span>
                                                    <input
                                                            className="replyRcontent"
                                                            value={ rr.rcontent }
                                                            readOnly = { rr.readOnly }
                                                            onChange = { (e)=>onContentChange(e, rr.rno,i) }
                                                             />
                                                    <div class="replyBtn">
                                                        {
                                                        login != null && login.mno == rr.mno
                                                        ?<>
                                                            <button onClick={ (e)=>onDeleteHandler( e , rr.rno ) } >삭제</button>
                                                           <button onClick={ (e)=>onUpdateHandler( e , rr.rno,i ) } >{ rr.readOnly == true ? '수정' : '수정완료' }</button>
                                                         </>
                                                        : <div> </div>
                                                        }
                                                    </div>
                                                </div>)
                                            })
                                       }
                            </>;
                    setReplyList( [...replyList] ); console.log( replyList )
                }else{
                    replyList[i].cusHTML = ''
                    setReplyList( [...replyList] ); console.log( replyList )
                }

            }
        })

    }
       // 1. 현재 로그인된 회원이 들어왔으면
       const [ login , setLogin ] = useState( JSON.parse( sessionStorage.getItem('login_token') ) )
    //
    const onUpdateHandler = ( e , rno,i) =>{
        // 수정 버튼
        if( replyList[i].readOnly == true ){
            replyList[i].readOnly = false;
            alert( '수정후 완료 버튼을 눌러주세요.');
        }else{ // 수정 완료 버튼
            replyList[i].readOnly = true;
            props.onReplyUpdate( rno , replyList[i].rcontent  );
            alert( '수정 완료 ');
        }
        setReplyList( [...replyList] );

    }
    //
    const onContentChange = (e , rno,i) =>{
         replyList[i].rcontent = e.target.value;
         setReplyList( [...replyList] );
    }

    return (<>
        <ReplyInput onReplyWrite={ props.onReplyWrite } rindex={0} />
        <div className="replyCount"> 전체 댓글 {props.replyList.length}개 </div>
        {
             replyList.map( (r,i)=>{
                if( r.readOnly == undefined ){ r.readOnly = true; }
                return(<div className="replyBox">
                    <span className="replyMname"> { r.mname } </span> <span className="replyRdate"> { r.rdate } </span>
                    <input
                        className="replyRcontent"
                        value={ r.rcontent }
                        readOnly = { r.readOnly }
                        onChange = { (e)=>onContentChange(e, r.rno,i) }
                         />
                    <div class="replyBtn">
                        <button onClick={ (e)=>onRereplyHandler( e , r.rno ) } >답글</button>
                        {
                        login != null && login.mno == r.mno
                        ?<>
                            <button onClick={ (e)=>onDeleteHandler( e , r.rno ) } >삭제</button>
                            <button onClick={ (e)=>onUpdateHandler( e , r.rno,i ) } >{ r.readOnly == true ? '수정' : '수정완료' }</button>
                         </>
                        : <div> </div>
                        }
                    </div>
                    <div>{r.cusHTML}</div>
                </div>)
            })
       }
    </>)
}