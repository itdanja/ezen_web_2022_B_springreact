import React,{ useState , useEffect } from 'react';
import axios from 'axios';
import ReplyInput from './ReplyInput';

export default function ReplyList( props ) {

    const [ login , setLogin ] = useState( JSON.parse( sessionStorage.getItem('login_token') ) )

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
                              { /* 답글 댓글[ rindex= 본인 선택한 댓글 = 답글을 작성할 [부모]댓글번호 ] 작성하는 input */ }
                             <ReplyInput onReplyWrite = { props.onReplyWrite }  rindex = { rno } />
                                { /* 답글 출력 */ }
                                {
                                    r.rereplyDtoList.map( ( rr , j )=> {
                                       if( rr.readOnly == undefined ){ rr.readOnly = true ; }
                                       return(
                                           <div className="replyBox">
                                               <span className="replyMname"> { rr.mname } </span>
                                               <span className="replyRdate"> { rr.rdate } </span>
                                               { /* [과제] 답글 수정 오류 */}
                                               <input  value={ rr.rcontent }  className="replyRcontent" onChange={ (e)=> onRcontentChange( e , rr.rno , i , j ) }  readOnly = { rr.readOnly }   />
                                               <div class="replyBtn">

                                                   {  login != null && login.mno == rr.mno
                                                       ?<>
                                                               <button onClick={ (e)=>onUpdateHandler( e , rr.rno , i , j ) } >
                                                                   { rr.readOnly == true ? '수정' : '수정완료'}
                                                               </button>
                                                               <button onClick={ (e)=>onDeleteHandler( e , rr.rno ) } >삭제</button>
                                                           </>
                                                       :
                                                       <></>
                                                   }
                                               </div>
                                           </div>)
                                    })
                                }
                        </div>
                    }else{ // 해당 답글 구역 숨기기
                         replyList[i].cusHTML = ''
                    }
                }
          })
          setReplyList( [...replyList] );
    }

    // 4. 수정 핸들러
    const onUpdateHandler = ( e , rno , i , j  ) => {
        console.log( i +"    " + j );
        // Rcontent 읽기모드 해제
        if( replyList[i].readOnly == true){
            replyList[i].readOnly = false;  alert('수정후 완료 버튼을 눌러주세요');
        }else{  // Rcontent 읽기모드 적용
            replyList[i].readOnly = true;
            // 수정처리
            props.onReplyUpdate( rno ,  replyList[i].rcontent )
        }
        setReplyList([...replyList])
    }

    // 5. 댓글 내용 수정
    const onRcontentChange = ( e , rno , i , j  ) => {
        console.log( i +"    " + j );
        replyList[i].rcontent = e.target.value;
        setReplyList([...replyList])
    }

    return (<>
        <ReplyInput  onReplyWrite = { props.onReplyWrite } rindex = {0}  /> { /* 상위 댓글[ rindex=0 ] 작성하는 input */ }
        <div className="replyCount"> 전체 댓글 : { replyList.length } 개 </div>
        {
             replyList.map( (r , i )=>{
                { /* rcontent 읽기모드 설정값 저장하는 [r.readOnly]필드 만들기 */}
                if( r.readOnly == undefined ){ r.readOnly = true ; }
                return(
                    <div className="replyBox">
                        <span className="replyMname"> { r.mname } </span>
                        <span className="replyRdate"> { r.rdate } </span>
                        <input  value={ r.rcontent }  className="replyRcontent" onChange={ (e)=> onRcontentChange( e , r.rno , i) }  readOnly = { r.readOnly }   />
                        <div class="replyBtn">
                            <button onClick={ (e)=>onRereplyHandler( e , r.rno ) } >답글</button>
                            {  login != null && login.mno == r.mno
                                ?<>
                                        <button onClick={ (e)=>onUpdateHandler( e , r.rno , i ) } >
                                            { r.readOnly == true ? '수정' : '수정완료'}
                                        </button>
                                        <button onClick={ (e)=>onDeleteHandler( e , r.rno ) } >삭제</button>
                                    </>
                                :
                                <></>
                            }
                        </div>
                        { r.cusHTML } { /* API 없던 필드 */}
                    </div>)
            })
        }
    </>)
}

/* JSX 형식에서 함수에 매개변수 전달 */
/*  <마크업 이벤트 = { (e)=>{ 함수명( e , 매개변수 ) } } /> */
