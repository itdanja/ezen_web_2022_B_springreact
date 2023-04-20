// 교재 App컴포넌트 --> AppTodo 컴포넌트
import React , { useState , useEffect } from 'react';
import Todo from './Todo';
import { List , Paper , Container } from '@mui/material';
import AddTodo from './AddTodo';
import axios from 'axios'; // npm install axios [ install -> i ]

export default function AppTodo( props ){
    // 1.  // item  = { id : "0",title : "Hello World 1 ",done : true  }
    const [ items, setItems ] = useState(
        [ // array s
        ] // array end
    ) // useState 함수 end
    // 컴포넌트가 실행될때 한번 이벤트 발생
    useEffect( () => {
        // ajax : jquery 설치 가 필요
        // fetch : 리액트 전송 비동기 통신 함수 [ 내장함수 - 설치 X ]
        // axios : 리액트 외부 라이브러리 [ 설치 필요 ] JSON통신 기본값
        axios.get( "http://localhost:8080/todo" )
            .then( r => {
                console.log( r );
                setItems( r.data ); // 서버에게 응답받은 리스트를 재렌더링
            })
        // 해당 주소의 매핑되는 컨트롤/메소드 에 @CrossOrigin(origins = "http://localhost:3000") 추가
            //axios.post( "http://localhost:8080/todo" , { mname :  "유재석" } ).then( r => { console.log( r ); })
            //axios.put( "http://localhost:8080/todo" ).then( r => { console.log( r ); })
            //axios.delete( "http://localhost:8080/todo" , { params : { id : 1 } }  ).then( r => { console.log( r ); })
     } ,[] )

    // 2. items에 새로운 item 등록하는 함수
    const addItem = ( item ) =>{ // 함수로부터 매개변로 전달은 item
        item.id = "ID-"+items.length     // ID 구성 // ??? DB PK 사용
        item.done = false;              // 체크 여부
        setItems( [...items , item ] ); // 기존 상태 items 에 item 추가
        // item = { title : "입력받은값" , id = "id-배열길이" , done = "기본값false" }
        // setItems( [ ...상태명 , 추가할 데이터 ] );
        //axios.post( "http://localhost:8080/todo" , { mname :  "유재석" } ).then( r => { console.log( r ); })
    }
    // 3. items에 item 삭제
    const deleteItem = (item)=>{
        // 만약에 items에 있는 item중 id와 삭제할 id 와 다른경우 해당 item 반환
        const newItems = items.filter( i =>  i.id !== item.id );
            // * 삭제할 id를 제외한 새로운 newItems 배열이 선언
        setItems( [...newItems ] );
       //axios.delete( "http://localhost:8080/todo" , { params : { id : 1 } }  ).then( r => { console.log( r ); })
    }

    // 4. 수정함수
    const editItem = ()=>{
        setItems([...items]) // 재 렌더링
    }

    // 반복문 이용한 Todo 컴포넌트 생성
    let TodoItems =
        /*<Paper style="margin : 16px;"> // HTML의 style 속성 방법 */
        <Paper style={{ margin : 16 }}>
            <List>
                {
                    items.map( (i)=>
                        <Todo
                            item ={ i }
                            key = { i.id }
                            삭제함수 = { deleteItem }
                            수정함수 = { editItem } />
                    )
                }
            </List>
        </Paper>
    return (<>
        <div className="App">
            <Container maxWidth="md">
                <AddTodo 저장함수={ addItem }  />
                { TodoItems }
            </Container>
        </div>
    </>);
}

// JS 반복문 함수 제공
    // r = [ 1 , 2 , 3 ]
    // 배열/리스트.forEach( (o) => { } ) : 반복문만 가능 [ return 없음 ]
        // let array = r.forEach( ( o ) => {   o+3  } );
        // 반복문이 끝나면 array 에는 아무것도 들어있지 않다.

    // 배열/리스트.map( (o) => { } )     : + return 값들 을 새로운 배열에 저장
        // let array = r.map( ( o ) => {   o+3  } );
        // 반복문이 끝나면 array 에는 [ 4 , 5 , 6 ]  ]

    // 배열/리스트.filter( (o) => { } )  : 조건충족할경우 객체 반환
        // let array = r.filter( ( o ) => {   o >= 3  } );
        // 반복문이 끝나면 array 에는 [ 3 ]  ]