import React,{ useEffect , useState , useRef } from 'react';
import Container from '@mui/material/Container';

export default function Chatting(props){

    // 1. 웹소켓 = webSocket = JS
    let webSocket = useRef( null );
    // 2.
    useEffect( () => {
        if( !webSocket.current ){
            webSocket.current = new webSocket('ws://localhost:8080/chat');

            // 1. 웹소켓이 접속 성공했을때.
            webSocket.current.onopen = () =>  { console.log(webSocket);  }
        }
    })

    return (<>
        <Container>
           <h6>익명 채팅방</h6>
           <div className="chatContentBox">

           </div>
           <div className="chatInputBox">
                <span> 익명94 </span>
                <input type="text" />
                <button>전송</button>
           </div>
        </Container>
    </>)
}
/*
    JSX : html + js     {   }
*/