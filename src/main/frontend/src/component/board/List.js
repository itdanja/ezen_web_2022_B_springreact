import React,{ useState , useEffect } from 'react';
import axios from 'axios';
/* ---------table mui -------- */
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
/* ---------------------------*/
import Container from '@mui/material/Container';
import Button from '@mui/material/Button';

import CategoryList from './CategoryList';

import Pagination from '@mui/material/Pagination';
import Stack from '@mui/material/Stack';

import Style from '../../css/board/list.css'


export default function List( props ) {
    // 1. 요청한 게시물 정보를 가지고 있는 리스트 변수[ 상태 관리변수 ]
    let [ rows , setRows ] = useState( [] )
    let [ pageInfo , setPageInfo ] = useState( { 'cno' : 0  , 'page' : 1 , key : '' , keyword : ''  } )
    let [ totalPages , setTotalPages ] = useState( 1 )

    // 2. 서버에게 요청하기 [ 컴포넌트가 처음 생성 되었을때 ]
    useEffect( ()=>{
        axios.get('/board',{ params : pageInfo })
            .then( r => {
                console.log(r);
                setRows( r.data.boards );
                setTotalPages( r.data.totalpages ) ;
            })
            .catch( err => { console.log(err); })
    } , [pageInfo] ) // cno 변경될때마다 해당 useEffect 실행된다.

    // 3. 카테고리 변경
    const categoryChange = ( cno ) => { pageInfo.cno = cno; setPageInfo( {...pageInfo} ); }

    const onSearch = () => {
        setPageInfo(
            {   bcno : pageInfo.bcno ,  // 카테고리 번호 [ 기존 그대로 : pageInfo.bcno ]
                page : 1 ,              // 검색시 첫페이지부터 보여주기 [ 1 ]
                key : document.querySelector('.key').value ,    // 검색할 필드명
                keyword: document.querySelector('.keyword').value  } // 검색할 단어
        )

    }

        const onPage = ( e ) =>{
            console.log( e.target.outerText )
            pageInfo.page = e.target.outerText;
            setPageInfo( {...pageInfo} );
        }



    return (
    <Container>
        <div style={{ display:'flex' , justifyContent : 'space-between' , alignItems : 'center' }}>
            <CategoryList categoryChange = { categoryChange } />
            <a href="/board/write"><Button variant="outlined"> 게시물 작성 </Button></a>
        </div>
        <TableContainer component={Paper}>
          <Table sx={{ minWidth: 650 }} aria-label="simple table">
            <TableHead>
              <TableRow>
                <TableCell align="center" style={{ width:'8%' }}>번호</TableCell>
                <TableCell align="center" style={{ width:'60%' }}>제목</TableCell>
                <TableCell align="center" style={{ width:'10%' }}>작성자</TableCell>
                <TableCell align="center" style={{ width:'10%' }}>작성일</TableCell>
                <TableCell align="center" style={{ width:'10%' }}>조회수</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {rows.map((row) => (
                <TableRow  key={row.name}   sx={{ '&:last-child td, &:last-child th': { border: 0 } }}  >
                  <TableCell align="center" component="th" scope="row"> {row.bno} </TableCell>
                  <TableCell align="left"> <a href={ '/board/view/'+row.bno }> {row.btitle} </a> </TableCell>
                  <TableCell align="center">{row.mname}</TableCell>
                  <TableCell align="center">{row.bdate}</TableCell>
                  <TableCell align="center">{row.bview}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
        <div style={{
            display: 'flex',
            justifyContent: 'center',
            margin: '40px 0px'
        }}>
              <Pagination count={totalPages} color="primary" onChange={ onPage } />
        </div>

        <div className="searchBox">
            <select className="key">
                <option value="btitle">제목</option>
                <option value="bcontent">내용</option>
            </select>
            <input type="text" className="keyword" />
            <button type="button" onClick={ onSearch }> 검색 </button>
        </div>

    </Container>
    );
}