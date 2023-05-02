import React,{ useState , useEffect } from 'react';
import axios from 'axios';
import styles from '../../css/board/board.css'

export default function ReplyInput( props ) {
    // 1. 입력받은 댓글 내용 저장하는 변수
    const [ rcontent , setContent ] = useState( '');
    // 2. 입력할때 마다 [ 체인지 될때마다 ]
    const rcontentHandler = (e) =>{ setContent( e.target.value );}
    // 3. 댓글 작성 버튼을 클릭했을때 [ 작성 ]
    const replywirteHandler = (e) => {
        // view.js --> replylist.js --> replyinput 로부터 전달받은 메소드 실행
        props.onReplyWrite( rcontent , props.rindex );
        setContent( ''); // 댓글 작성시 공백 초기화
    }
    return (<div className="replyInputBox">
        <textarea
                value={ rcontent }
                onChange = { rcontentHandler }
                className="rcontent" type="text" />
        <button className="replywirtebtn" onClick={ replywirteHandler }> 댓글작성 </button>
    </div>)

}