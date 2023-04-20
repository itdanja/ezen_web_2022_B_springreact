// p. 183 컴포넌트 만들기
import React , { useState } from 'react';
/*
    npm install @mui/material @emotion/react @emotion/styled
    npm install @mui/material @mui/styled-engine-sc styled-components
*/
import { ListItem , ListItemText , InputBase , Checkbox } from '@mui/material';

export default function Todo(props) {
    console.log( props )
    // 1. Hook 상태관리 useState 함수
    const [ item , setItem ] = useState( props.item );

    return ( <>
        <ListItem>
            <Checkbox checked={item.done} />
            <ListItemText>
                <InputBase
                    type="text"
                    id={ item.id }
                    name={ item.id }
                    value={ item.title }
                    multiline={true}
                    fullWidth={true}
                />
            </ListItemText>
        </ListItem>
    </>);
}