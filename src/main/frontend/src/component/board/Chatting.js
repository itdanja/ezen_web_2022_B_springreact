import React,{ useEffect , useState , useRef } from 'react';
import Container from '@mui/material/Container';
import styles from '../../css/board/chatting.css'
import axios from 'axios';

export default function Chatting(props){

    let [ id , setId ] = useState(''); // 익명채팅에서 사용할 id [ 난수 저장 ]
    let [ msgContent , setMsgContent ] = useState([]); // 현재 채팅중인 메시지 를 저장하는 변수
    let msgInput = useRef(null); // 채팅입력창[input] DOM객체 제어 변수
    let fileForm = useRef(null); // 채팅입력창[input] DOM객체 제어 변수
    let fileInput = useRef(null); // 채팅입력창[input] DOM객체 제어 변수

    // 1. 재렌더링 될때마다 새로운 접속
    // let 클라이언트소켓 = new WebSocket("ws/localhost:8080/chat")  ;
    // 2. 재렌더링 될때 데이터 상태 유지
    let ws = useRef( null ) ;   // 1.모든 함수 사용할 클라이언트소켓 변수
    useEffect( ()=>{            // 2. 컴포넌트 실행시 1번만 실행
        if( !ws.current ){      // 3.클라이언트소켓이 접속이 안되어있을때. [ * 유효성검사 ]
            ws.current = new WebSocket("ws://localhost:8080/chat") ; // 4. 서버소켓 연결
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
                setMsgContent( (msgContent)=> [...msgContent , data  ] ); // 재 렌더링
            }
        }
    } );


    // 4.메시지 전송
    const onSend = () =>{ // msgInput변수가 참조중인 <input ref={ msgInput } > 해당 input 를 DOM객체로 호출
        // 1. 메시지 전송
        let msgBox ={ id : id,  msg : msgInput.current.value,  time : new Date().toLocaleTimeString(), type : 'msg'  }
        if( msgBox.msg != ''){ // 내용이 있으면 메시지 전송
                ws.current.send( JSON.stringify( msgBox ) ); // 클라이언트가 서버에게 메시지 전송 [ .send( ) ]
                msgInput.current.value = '';
        }
        // 2. 첨부파일 전송 [ axios 이용한 서버에게 첨부파일 업로드 ]
        if( fileInput.current.value != '' ){ // 첨부파일 존재하면
            axios.post( "/chat/fileupload" ,  new FormData( fileForm.current ) )
                    .then( r => { console.log( r.data) } );
        }
    }

    // 5. 메시지 받기 렌더링 할때마다 스크롤 가장 하단으로 내리기
    useEffect ( () => {
        document.querySelector('.chatContentBox').scrollTop = document.querySelector('.chatContentBox').scrollHeight;
    },[msgContent])

    let aa = 10;

    return (<>
        <Container>
           <div className="chatContentBox">
           {
                msgContent.map( (m)=>{
                    return(<>
                       {/* 조건 스타일링 : style={ 조건 ? {참일경우} , {거짓일경우} }  */}
                        <div className="chatContent" style={ m.id == id ? { backgroundColor: '#d46e6e' } : { } }  >
                            <span> { m.id } </span>
                            <span> { m.time } </span>
                            <span> { m.msg } </span>
                        </div>
                    </>)
                })
           }
           </div>
           <div className="chatInputBox">
                <span> { id }  </span>
                <input className="msgInput" ref={ msgInput } type="text" />
                <button onClick={ onSend }>전송</button>
                <form ref={fileForm}>
                    <input ref={ fileInput } type="file" name="attachFile"/>
                </form>
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