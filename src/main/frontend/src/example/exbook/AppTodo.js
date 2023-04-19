// 교재 App컴포넌트 --> AppTodo 컴포넌트
import React , { useState , useEffect  } from 'react';
import Todo from './Todo';
import { List , Paper , Container } from '@mui/material';
import AddTodo from './AddTodo';
import axios from 'axios'; // 터미널 npm i axios

export default function AppTodo( props ){
    // 1.  // item  = { id : "0",title : "Hello World 1 ",done : true  }
    const [ items, setItems ] = useState(
        [ // array s
        ] // array end
    ) // useState 함수 end

    useEffect(() => {
        axios.get("http://localhost:8080/todo/get" ).then( r => { console.log( r );

            setItems( r.data )

         } ) .catch( err => { console.log( err); } )
    },[])

      const editItem = () => {
        setItems([...items]);
      };


       const deleteItem = (item) => {
         // 삭제할 아이템을 찾는다.
         const newItems = items.filter(e => e.id !== item.id);
         // 삭제할 아이템을 제외한 아이템을 다시 배열에 저장한다.

        console.log( item.id )
         setItems([...newItems]);

        axios.delete("http://localhost:8080/todo/delete", { params : { id : item.id } } ) .then( r => {  console.log( r ); } )

       }

    // 2. items에 새로운 item 등록하는 함수
    const addItem = ( item ) =>{ // 함수로부터 매개변로 전달은 item
        item.id = "ID-"+items.length     // ID 구성
        item.done = false;              // 체크 여부
        setItems( [...items , item ] ); // 기존 상태 items 에 item 추가
        // setItems( [ ...상태명 , 추가할 데이터 ] );
        axios.post("http://localhost:8080/todo/post" , item ).then( r => { console.log( r ); } )
    }

    // 반복문 이용한 Todo 컴포넌트 생성
    let TodoItems =
        /*<Paper style="margin : 16px;"> // HTML의 style 속성 방법 */
        <Paper style={{ margin : 16 }}>
            <List>
                {
                    items.map( (i)=>
                        <Todo item ={ i } id = { i.id } key={i.id}  deleteItem={deleteItem} editItem={editItem} />
                    )
                }
            </List>
        </Paper>
    return (<>
        <div className="App">
            <Container maxWidth="md">
                <AddTodo addItem={ addItem }  />
                { TodoItems }

                                  <h3>로그인</h3>
                                  <form method="post" action="http://localhost:8080/member/login">
                                      이메일 : <input type="text" name="memail" /><br/>
                                      비밀번호 : <input type="password" name="mpassword" /><br/>
                                      <input type="submit" value="로그인" />
                                      <a href="http://localhost:8080/oauth2/authorization/kakao"> 카카오 로그인 </a>
                                      <a href="http://localhost:8080/oauth2/authorization/naver"> 네이버 로그인 </a>
                                      <a href="http://localhost:8080/oauth2/authorization/google"> 구글 로그인 </a>
                                  </form>


            </Container>
        </div>
    </>);
}