import React,{ useState , useEffect } from 'react';
import axios from 'axios';
import Style from '../../css/board/write.css'

import Container from '@mui/material/Container';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';

import CategoryList from './CategoryList'

export default function Write( props ) {

    let [ cno , setCno ] = useState( 0 )

    // 1. 게시물 쓰기
    const setBoard = () => {
        let info = {
            btitle : document.querySelector("#btitle").value,
            bcontent : document.querySelector("#bcontent").value,
            cno : cno
        }
        axios.post( '/board' , info )
            .then( r => {
                if( r.data == 4 ){
                    alert('글쓰기 등록 ')
                    window.location.href='/board/list';
                }else if( r.data == 1 ){
                    alert('카테고리를 선택해주세요');
                }else if( r.data == 3 ){
                    alert('게시물 등록 실패 [ 관리자에게 문의] ')
                }else if( r.data == 2 ){
                    alert('로그인후 등록이 가능합니다.')
                }

             })
    }
    const backPage = () => {  window.location.href='/board/list'; }

    const categoryChange = ( cno ) => { setCno( cno ); }

    return(<>
        <Container>
            <CategoryList categoryChange = { categoryChange } />
            <TextField fullWidth className="btitle"     id="btitle"  label="제목" variant="standard" />
            <TextField fullWidth className="bcontent"   id="bcontent" label="내용" multiline rows={10} variant="standard" />
            <div className="writeBtnBOX">
                <Button variant="outlined" onClick={ setBoard }> 등록 </Button>
                <Button variant="outlined" onClick={ backPage }> 취소 </Button>
            </div>
        </Container>
    </>)
}