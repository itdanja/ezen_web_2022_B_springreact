
import React , { useState , useEffect , useRef } from 'react';
import Container from '@mui/material/Container';
import styles from '../../css/board/chatting.css'

export default function Chatting(props){
    const [ id , setId ] = useState( 0 ); // * 서버소켓과 연결 여부 저장하는 메모리
    const [ msgList , setMsgList ] = useState( [ ] );  // * 서버소켓으로 부터 들어온 메시지들을 저장하는 메모리

    // * 클라이언트소켓 [ 재 렌더링될때마다 변수 상태 유지 --> useRef 사용 ]
    let ws = useRef(null); // useState 수정될때마다 해당 컴포넌트 재렌더링 // * useRef : 재 렌더링될때 적용X
    useEffect( ()=>{  // * 컴포넌트 mount 될때 서버소켓 연결 , unmount 될때 서버소켓 닫기
        if( !ws.current ){ // 클라이언소켓이 없으면
            ws.current = new WebSocket('ws://localhost:8080/chat');   // 클라이언트 소켓 생성 [ 서버소켓 주소 ]
            // 1. 클라이언트 소켓이 open 될때
            ws.current.onopen  = () => {

                let randId = '익명' + Math.floor( Math.random() * (9999 - 1) + 1 );

                setId(randId);

            }
            // 2. 클라이언트 소켓이 close 될때
            ws.current.onclose = (e) => {   console.log("닫기:"+e);  }
            // 3. 클라이언트 소켓이 error 될때
            ws.current.onerror = (e) => {    console.log("에러:"+e); }
            // 4. 서버소켓으로부터 message 받았을때
            ws.current.onmessage = (e) => {
                let data = JSON.parse(e.data);
                setMsgList( (prevMsgList) => [ ...prevMsgList , data ] ); // 기존 값 + 새로운 값 => 상태 업데이트
            }
        }
    }  );
    // * 전송버튼 클릭했을때  send이벤트
    const onMsg = () => {
        let msg = document.querySelector('.msgInput').value;        // 1. 입력받은 데이터 가져오기

        let info = {
            id : id ,
            message : msg ,
            time : new Date().toLocaleTimeString()
        }

        ws.current.send( JSON.stringify( info ) );     // 2. 웹소켓 이용한 send 메소드 사용 [ JSON 형식 으로 보내기  ]
        document.querySelector('.msgInput').value =''               // 3. 입력상자 초기화
    }
    useEffect( ()=>{
    document.querySelector('.chatBox').scrollTop =  document.querySelector('.chatBox').scrollHeight;
    },[msgList] );
    return (
        <Container>
            <div className="chatBox">
                {
                    msgList.map( (msg) => {
                        return(<>
                            {
                                msg.id == id
                                ?(
                                    <div className="chat-message" style={{ backgroundColor: '#ffdcdc' }} >
                                        <span> { msg.id} </span>
                                        <span> { msg.time} </span>
                                        <span> { msg.message} </span>
                                     </div>
                                )
                                :(
                                    <div className="chat-message">
                                        <span> { msg.id} </span>
                                        <span> { msg.time} </span>
                                        <span> { msg.message} </span>
                                     </div>
                                )
                            }
                         </>)
                    })
                }
            </div>
            <div className="chatInputBox">
                <span> { id } </span>
                <input type="text" className="msgInput" />
                <button type="button" onClick={ onMsg } > 전송 </button>
            </div>
        </Container>
    )



};