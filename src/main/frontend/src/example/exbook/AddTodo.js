import React,{ useState } from 'react';
import { Button , Grid , TextField } from '@mui/material'

export default function AddTodo( props ){
    // 사용자가 입력한 데이터를 저장할 상태변수
    const [item , setItem ] = useState( {title : "" } )

    //1. 사용자가 입력한 데이터를 가져오는 함수
    // onChange={ onInputChange }
    // onChange 이벤트속성 : 해당 요소가 해당 이벤트를 발생했을때 이벤트 정보를 반환[ e ]
    // onChange이벤트정보 e를  onInputChange 함수로 매개변수 e 전달
    const onInputChange = ( e )=>{
            // console.log( e ); // 해당 이벤트가 발생했을때의 이벤트정보 들어있는 객체
            // console.log( e.target ); // 해당 이벤트가 발생된 요소 [ TextField ]
        setItem( { title : e.target.value } );  // 상태변경 : set~~~  : 입력받은 값을 가져와서 상태변수를 수정
            console.log( item );
    }

    return (<>
        <Grid container style={{ marginTop : 20 }}>
            <Grid xs={11} md={11} item style={{ paddingRight:16}}>
                    <TextField
                        placeholder="여기에 Todo 작성"
                        fullWidth
                        onChange={ onInputChange }
                    />
            </Grid>
            <Grid xs={1} md={1} item >
                <Button fullWidth style={{ height : '100%' }} color="secondary" variant="outlined">
                    +
                </Button>
            </Grid>
        </Grid>
    </>)
}