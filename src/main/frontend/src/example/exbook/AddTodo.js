import React,{ useState } from 'react';
import { Button , Grid , TextField } from '@mui/material'

export default function AddTodo( props ){
    // 사용자가 입력한 데이터를 저장할 상태변수
    const [item , setItem ] = useState( {title : "" } )
    return (<>
        <Grid container style={{ marginTop : 20 }}>
            <Grid xs={11} md={11} item style={{ paddingRight:16}}>
                    <TextField placeholder="여기에 Todo 작성" fullWidth />
            </Grid>
            <Grid xs={1} md={1} item >
                <Button fullWidth style={{ height : '100%' }} color="secondary" variant="outlined">
                    +
                </Button>
            </Grid>
        </Grid>
    </>)

}