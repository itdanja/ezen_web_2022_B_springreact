import React,{ useEffect , useState , useRef } from 'react';
import Container from '@mui/material/Container';
import styles from '../../css/board/chatting.css'
import axios from 'axios';

export default function Chatting(props){

    let [ id , setId ] = useState(''); // 익명채팅에서 사용할 id [ 난수 저장 ]
    let [ msgContent , setMsgContent ] = useState([]); // 현재 채팅중인 메시지 를 저장하는 변수
    let msgInput = useRef(null); // 채팅입력창[input] DOM객체 제어 변수
    let fileForm = useRef(null); //
    let fileInput = useRef(null); //
    let chatContentBox = useRef(null); //

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
            let formData = new FormData( fileForm.current )
            fileAxios(  formData ) // 파일 전송
        }
    }

    // 5. 메시지 받기 렌더링 할때마다 스크롤 가장 하단으로 내리기
    useEffect ( () => {
        document.querySelector('.chatContentBox').scrollTop = document.querySelector('.chatContentBox').scrollHeight;
    },[msgContent])

    // 6. 파일 전송 axios
    const fileAxios = (formData)=>{
          axios.post( "/chat/fileupload" , formData  )
            .then( r => {
                console.log( r.data)
                // 다른 소켓들에게 업로드 결과 전달
                let msgBox ={ id : id, msg : msgInput.current.value,
                    time : new Date().toLocaleTimeString(), type : 'file'  ,
                    fileInfo : r.data // 업로드 후 응답받은 파일정보
                }
                ws.current.send( JSON.stringify( msgBox ) );
                fileInput.current.value = '';
            } );
    }

    return (<>
        <Container>

           <div
                ref={chatContentBox}
                className="chatContentBox"
                onDragEnter = { (e)=>{ console.log('onDragEnter');
                    e.preventDefault();  {/* 상위 이벤트 제거  */ }
                } }
                onDragOver = { (e)=>{ console.log('onDragOver');
                    e.preventDefault(); { /* 상위 이벤트 제거 */ }
                    e.target.style.backgroundColor = '#e8e8e8';
                } }
                onDragLeave = { (e)=>{ console.log('onDragLeave');
                    e.preventDefault(); { /* 상위 이벤트 제거 */ }
                    e.target.style.backgroundColor = '#ffffff';
                } }
                onDrop ={ (e)=>{ console.log('onDrop');
                    e.preventDefault(); { /* 상위 이벤트 제거 */ }
                    { /* 드랍된 파일들을  호출 = e.dataTransfer.files;  */}
                    let files = e.dataTransfer.files;
                    for( let i = 0 ; i<files.length ; i++ ) {
                        {/*파일이 존재하면 */ }
                        if( files[i] != null && files[i] != undefined ) {
                            let formData = new FormData( fileForm.current )
                            {/* 드래그된 파일을 폼데이터 추가 */ }
                            formData.set( 'attachFile' , files[i]  )
                            fileAxios(  formData )
                        }
                    }
                } }
           >

           {
                msgContent.map( (m)=>{
                    return(<>
                       {/* 조건 스타일링 : style={ 조건 ? {참일경우} , {거짓일경우} }  */}
                        <div className="chatContent" style={ m.id == id ? { backgroundColor: '#d46e6e' } : { } }  >
                            <span> { m.id } </span>
                            <span> { m.time } </span>
                            {
                                m.type == 'msg' ? <span> { m.msg } </span>
                                : (<>
                                    <span>
                                        <span> { m.fileInfo.originalFilename } </span>
                                        <span> { m.fileInfo.sizeKb } </span>
                                        <span>
                                            <a href={"/chat/filedownload?uuidFile=" + m.fileInfo.uuidFile } > 저장 </a>
                                        </span>
                                    </span>
                                </>)
                            }

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
                    <input
                        ref={ fileInput }
                        type="file"
                        name="attachFile"
                    />
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