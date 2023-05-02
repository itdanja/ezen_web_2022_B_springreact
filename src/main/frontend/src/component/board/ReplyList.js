import React,{ useState , useEffect } from 'react';
import axios from 'axios';
import ReplyInput from './ReplyInput';

export default function ReplyList( props ) {

    // props 으로 부터전달 받은 댓글 리스트 관리하는 상태변수
    let [ replyList , setReplyList ] = useState( [] );
    // props 변경 되었을때 [  view.js axios 실행 ]
    useEffect( () => { setReplyList( props.replyList ) } , [props.replyList] )

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
    // 3. 답글 핸들러
    const onRereplyHandler= ( e , rno ) => {
          console.log( '답글' + rno );

          replyList.forEach( (r , i) => { // 전체 댓글
                // 전체 댓글 번호중에 본인 선택한 댓글번호와 같으면
                if( r.rno == rno ){
                    if( r.cusHTML == '' || r.cusHTML == undefined ){
                        // 새로운 필드에 새로운 html[JSX] 구성
                        replyList[i].cusHTML = <div>
                             <ReplyInput
                                 onReplyWrite = { props.onReplyWrite }
                                 rindex = { rno }
                             />
                             { /* 답글 댓글[ rindex= 본인 선택한 댓글 = 답글을 작성할 [부모]댓글번호 ] 작성하는 input */ }
                        </div>
                    }else{ // 해당 답글 구역 숨기기
                         replyList[i].cusHTML = ''
                    }
                }
          })
          setReplyList( [...replyList] );
    }

    return (<>
        { /* 상위 댓글[ rindex=0 ] 작성하는 input */ }
        <ReplyInput
            onReplyWrite = { props.onReplyWrite }
            rindex = {0}
        />

        <h6> 댓글 목록 </h6>
        {
             replyList.map( (r)=>{
                return(<div>
                        <span> { r.rcontent } </span>
                        <span> { r.rdate } </span>

                        <button onClick={ (e)=>onRereplyHandler( e , r.rno ) } >답글</button>
                        <button onClick={ (e)=>onDeleteHandler( e , r.rno ) } >수정</button>
                        <button onClick={ (e)=>onDeleteHandler( e , r.rno ) } >삭제</button>
                        { r.cusHTML } { /* API 없던 필드 */}
                    </div>)
            })
        }
    </>)
}

/* JSX 형식에서 함수에 매개변수 전달 */
/*  <마크업 이벤트 = { (e)=>{ 함수명( e , 매개변수 ) } } /> */
