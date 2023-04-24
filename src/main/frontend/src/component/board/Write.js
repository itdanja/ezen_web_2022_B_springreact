import React,{ useState , useEffect } from 'react';
import axios from 'axios';

import Container from '@mui/material/Container';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';

import CategoryList from './CategoryList'

export default function Write( props ) {

    // 1. 게시물 쓰기
    const setBoard = () => {
        let info = {
            btitle : document.querySelector("#btitle").value,
            bcontent : document.querySelector("#bcontent").value,
            cno : 1
        }
        console.log( info );

        axios.post( 'http://localhost:8080/board/write' , info )
            .then( r => { console.log(r); })
    }

    return(<>
        <Container>
            <CategoryList />
            <TextField fullWidth className="btitle"     id="btitle"  label="제목" variant="standard" />
            <TextField fullWidth className="bcontent"   id="bcontent" label="내용" multiline rows={10} variant="standard" />
            <Button variant="outlined" onClick={  setBoard }> 등록 </Button>
            <Button variant="outlined"> 취소 </Button>
        </Container>
    </>)
}