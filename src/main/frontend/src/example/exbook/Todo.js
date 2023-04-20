// p. 183 컴포넌트 만들기
import React , { useState } from 'react';
/*
    // 외부 컴포넌트 호출
    npm install @mui/material @emotion/react @emotion/styled
    npm install @mui/material @mui/styled-engine-sc styled-components
    npm install @mui/icons-material
*/
import {
    ListItem , ListItemText , InputBase ,
    Checkbox , ListItemSecondaryAction , IconButton }
from '@mui/material';
// 삭제 아이콘 // npm install @mui/icons-material
import DeleteOutlined from "@mui/icons-material/DeleteOutlined";

export default function Todo(props) {

    console.log( props )
    // 1. Hook 상태관리 useState 함수
    const [ item , setItem ] = useState( props.item );
    // 2. props 전달된 삭제함수 변수로 이동
    const deleteItem = props.삭제함수;
    // 3. 삭제함수 이벤트처리 핸들러
    const deleteEventHandler = ()=>{
        deleteItem(item);
    }
    // 4. readOnly = true 초기화가 된 필드/변수 와 해당 필드를 수정할수 있는 함수 setReadOnly [배열]반환
    const [readOnly , setReadOnly ] = useState( true );

    // 5. 읽기모드 해제  => 수정 가능
    const turnOffReadOnly = () => { console.log("turnOffReadOnly")
        setReadOnly(false); // readOnly = true 수정불가능  false 수정가능
    }
    // 6. 엔터키를 눌렀을때 -> 수정 금지
    const turnOnReadOnly = (e) => {  console.log("turnOnReadOnly")
        if( e.key == "Enter"){
            setReadOnly(true);
            //axios.put( "http://localhost:8080/todo" ).then( r => { console.log( r ); })
        }
    }
    // 7. 입력받은 값을 변경
    let editItem = props.수정함수
    const editEventHandler = (e)=>{  console.log("editEventHandler")
        item.title = e.target.value; // InputBase 에 값이 변경될때마다 상태변수에 입력된 값 저장
        editItem(); // 상위 컴포넌트 렌더링
    }
    // 8. 체크박스 업데이트
    const checkboxEventHandler = (e)=>{
        item.done = e.target.checked; //  checked : 체크일경우 true  아니면 false
        editItem(); // 상위 컴포넌트 렌더링
    }

    return ( <>
        <ListItem>

            <Checkbox checked={item.done}  onChange = { checkboxEventHandler } />
            <ListItemText>
                <InputBase inputProps = {{ readOnly : readOnly }}
                    onClick = { turnOffReadOnly }
                    onKeyDown = { turnOnReadOnly  }
                    onChange = { editEventHandler }
                    type="text"
                    id={ item.id }
                    name={ item.id }
                    value={ item.title }
                    multiline={true}
                    fullWidth={true}
                />
            </ListItemText>
                <ListItemSecondaryAction>
                    <IconButton onClick={ deleteEventHandler }>
                        <DeleteOutlined />
                    </IconButton>
                </ListItemSecondaryAction>
        </ListItem>
    </>);
}