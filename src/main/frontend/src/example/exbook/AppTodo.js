// 교재 App컴포넌트 --> AppTodo 컴포넌트
import React , { useState } from 'react';
import Todo from './Todo';
import { List , Paper , Container } from '@mui/material';
import AddTodo from './AddTodo';

export default function AppTodo( props ){
    // 1.  // item  = { id : "0",title : "Hello World 1 ",done : true  }
    const [ items, setItems ] = useState(
        [ // array s
        ] // array end
    ) // useState 함수 end

    // 2. items에 새로운 item 등록하는 함수
    const addItem = ( item ) =>{ // 함수로부터 매개변로 전달은 item
        item.id = "ID-"+item.length     // ID 구성
        item.done = false;              // 체크 여부
        setItems( [...items , item ] ); // 기존 상태 items 에 item 추가
        // setItems( [ ...상태명 , 추가할 데이터 ] );
    }

    // 반복문 이용한 Todo 컴포넌트 생성
    let TodoItems =
        /*<Paper style="margin : 16px;"> // HTML의 style 속성 방법 */
        <Paper style={{ margin : 16 }}>
            <List>
                {
                    items.map( (i)=>
                        <Todo item ={ i } key = { i.id }/>
                    )
                }
            </List>
        </Paper>
    return (<>
        <div className="App">
            <Container maxWidth="md">
                <AddTodo addItem={ addItem }  />
                { TodoItems }
            </Container>
        </div>
    </>);
}