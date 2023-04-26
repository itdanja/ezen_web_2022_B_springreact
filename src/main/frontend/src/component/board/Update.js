import React,{ useState , useEffect } from 'react';
import axios from 'axios';
import { useSearchParams  } from 'react-router-dom';

import Container from '@mui/material/Container';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import CategoryList from './CategoryList'

export default function Update( props ) {
   const [ searchParams , setSearchParams ]  = useSearchParams();
   const [ board , setBoard ] = useState( {} );
   let [ cno , setCno ] = useState( 0 );

   useEffect( ()=>{
        axios.get("/board/getboard" , { params : { bno : searchParams.get("bno") }})
            .then( (r) => {
                console.log( r.data );
                setBoard( r.data );
                setCno( r.data.cno )
            })
   } , [ ] )

    // 수정 함수
    const onUpdate  = () => {
        // axios
    }
    // 카테고리 변경 함수
    const categoryChange = (cno) =>{   setCno( cno );   }

    //  제목 입력 이벤트
    const inputTitle = (e) => {
        console.log(e.target.value );
        board.btitle = e.target.value;
        setBoard( {...board} )
    }
    // 내용 입력 이벤트
     const inputContent = (e) => {
        console.log(e.target.value );
        board.bcontent = e.target.value;
        setBoard( {...board} )
    }
    return (<>
         <Container>
             <CategoryList categoryChange={  categoryChange } />
             <TextField fullWidth value={ board.btitle } onChange={ inputTitle  } className="btitle"     id="btitle"  label="제목" variant="standard" />
             <TextField fullWidth value={ board.bcontent } onChange={  inputContent } className="bcontent"   id="bcontent" label="내용" multiline rows={10} variant="standard" />
             <Button variant="outlined" onClick={  onUpdate }> 수정 </Button>
             <Button variant="outlined"> 취소 </Button>
         </Container>
    </>)
}