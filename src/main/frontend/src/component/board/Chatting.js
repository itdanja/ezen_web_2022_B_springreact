import React,{ useEffect , useState , useRef } from 'react';
import Container from '@mui/material/Container';

export default function Chatting(props){

    let [ id , setId ] = useState(''); // 익명채팅에서 사용할 id [ 난수 저장 ]
    let [ msgContent , setMsgContent ] = useState([]); // 현재 채팅중인 메시지 를 저장하는 변수
    let msgInput = useRef(null); // 채팅입력창[input] DOM객체 제어 변수
    // 1. 재렌더링 될때마다 새로운 접속
    // let 클라이언트소켓 = new WebSocket("ws/localhost:8080/chat")  ;
    // 2. 재렌더링 될때 데이터 상태 유지
    let ws = useRef( null ) ;
    useEffect( ()=>{
        if( !ws.current ){
            ws = new WebSocket("ws://localhost:8080/chat")  ;
             // 3. 서버소켓에 접속했을때 이벤트
            ws.current.onopen = () => {   console.log('서버 접속했습니다.');
                let randId = Math.floor( Math.random() * ( 9999 - 1 ) + 1  );    setId( '익명' + randId );
            }
            // 3. 서버소켓에 나갔을때 이벤트
            ws.current.onclose = (e) => { console.log('서버 나갔습니다.'); }
            // 3. 서버소켓과 오류 발생했을때 이벤트
            ws.current.onerror = (e) => { console.log('소켓 오류'); }
            // 3. 서버소켓으로 부터 메시지 받았을때 이벤트
            ws.current.onmessage = (e) => {  console.log('서버소켓으로 메시지 받음');  console.log( e);  console.log( e.data );
                //msgContent.push(e.data);    // 배열에 내용 추가
                //setMsgContent( [...msgContent] ); // 재 렌더링
                let data = JSON.parse( e.data );
                setMsgContent( (msgContent) => [...msgContent , data  ] ); // 재 렌더링
            }
        }
    } );
    // 4.메시지 전송
    const onSend = () =>{
        // msgInput변수가 참조중인 <input ref={ msgInput } > 해당 input 를 DOM객체로 호출
        let msgBox ={
            id : id, // 보낸 사람
            msg : msgInput.current.value, // 보낸 내용
            time : new Date().toLocaleTimeString() // 현재 시간만
        }
        ws.current.send( JSON.stringify( msgBox ) ); // 클라이언트가 서버에게 메시지 전송 [ .send( ) ]
        msgInput.current.value = '';
    }

    return (<>
        <Container>
           <h6>익명 채팅방</h6>
           <div className="chatContentBox">
           {
                msgContent.map( (m)=>{
                    return(<><div> { m } </div></>)
                })
           }
           </div>
           <div className="chatInputBox">
                <span> { id }  </span>
                <input ref={ msgInput } type="text" />
                <button onClick={ onSend }>전송</button>
           </div>
        </Container>
    </>)
}
/*
    JSX : html + js     {   }


    let 숫자 = 10;                            // 지역변수 : 컴포넌트[함수] 호출 될때마다 초기화
    let 숫자2 = useRef(10);                   // 재렌더링시 초기값이 적용되지 않는 함수 [ 반환값 : 객체{ current : 값 } ]

    console.log( 숫자 )
    console.log( 숫자2 )
    console.log( id )
    // 2. 난수 생성
    let randId = Math.floor( Math.random() * ( 9999 -1 ) +1 );
        // Math.floor( Math.random() * ( 최댓값 - 최소값 ) + 최소값 ); : 정수 1~9999
    숫자 = randId;
    숫자2.current = randId;

    // setId( randId );

*/