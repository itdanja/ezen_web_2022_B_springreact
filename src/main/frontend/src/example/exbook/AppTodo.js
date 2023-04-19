// 교재 App컴포넌트 --> AppTodo 컴포넌트
import React , { useState } from 'react';
import Todo from './Todo';
export default function AppTodo( props ){
    // 1.  // item  = { id : "0",title : "Hello World 1 ",done : true  }
    const [ items, setItems ] = useState(
        [ // array s
            {
                id : "0",
                title : "Hello World 1 ",
                done : true
            },
            {
                id : "1",
                title : "Hello World 2 ",
                done : false
            }
        ] // array end
    ) // useState 함수 end
    // 반복문 이용한 Todo 컴포넌트 생성
    let TodoItems =
            items.map( (i)=>
                <Todo item ={ i } key = { i.id }/>
            )

    return (<>
        <div className="App">
            { TodoItems }
        </div>
    </>);
}