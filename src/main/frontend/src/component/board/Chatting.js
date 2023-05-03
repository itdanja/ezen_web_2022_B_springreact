import React,{ useEffect , useState , useRef } from 'react';
import Container from '@mui/material/Container';

export default function Chatting(props){

    let 숫자 = 10;                            // 지역변수 : 컴포넌트[함수] 호출 될때마다 초기화
    let 숫자2 = useRef(10);                   // 재렌더링시 초기값이 적용되지 않는 함수 [ 반환값 : 객체{ current : 값 } ]
    let inputRef = useRef(null);            // document.querySelector vs useRef

    const [ id , setId ] = useState(0); // set메소드 사용시 컴포넌트 재호출[재렌더링]

/*    // 1. 웹소켓 = webSocket = JS
    let webSocket = useRef( null );
    // 2.
    useEffect( () => {
        if( !webSocket.current ){
            webSocket.current = new webSocket('ws://localhost:8080/chat');
            // 1. 웹소켓이 접속 성공했을때.
            webSocket.current.onopen = () =>  { console.log(webSocket);  }
        }
    })
*/

    console.log( 숫자 )
    console.log( 숫자2 )
    console.log( id )

    // 2. 난수 생성
    let randId = Math.floor( Math.random() * ( 9999 -1 ) +1 );
        // Math.floor( Math.random() * ( 최댓값 - 최소값 ) + 최소값 ); : 정수 1~1000
    숫자 = randId;
    숫자2.current = randId;

    // setId( randId );

    const onSend = () =>{
        console.log( inputRef.current.value  )
        console.log( document.querySelector('.msgInput').value )
        inputRef.current.value = 3543;
    }
    return (<>
        <Container>
           <h6>익명 채팅방</h6>
           <div className="chatContentBox">

           </div>
           <div className="chatInputBox">
                <span> 익명94 </span>
                <input ref={ inputRef } type="text" className="msgInput" />
                <button onClick={ onSend }>전송</button>
           </div>
        </Container>
    </>)
}
/*
    JSX : html + js     {   }
*/