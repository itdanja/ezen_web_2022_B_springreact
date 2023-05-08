import React,{ useState , useEffect } from 'react';
import { DataGrid } from '@mui/x-data-grid';
import Container from '@mui/material/Container';
import axios from 'axios';

const columns = [
  { field: 'id', headerName: '번호', width: 130 },
  { field: 'pcategory', headerName: '카테고리', width: 130 },
  { field: 'pname', headerName: '제품명', width: 130 },

  {
    field: 'pprice',
    headerName: '가격',
    type: 'number',
    width: 130,
  },
    {
      field: 'pstate',
      headerName: '상태',
      type: 'number',
      width: 90,
    },
      {
        field: 'pstock',
        headerName: '재고',
        type: 'number',
        width: 90,
      },
            {
              field: 'pmanufacturer',
              headerName: '제조사',
              width: 90,
            },
  {
    field: 'pcomment',
    headerName: '제품설명',
    sortable: false,
    width: 300
  }
];

export default function DataTable() {

    const [ rows , setRows ] = useState([]);

    useEffect( ()=>{
        axios.get('/product').then( r => { setRows([...r.data]);} )
    },[])

    console.log( rows );

  return (
      <Container>
            <div style={{ height: 400, width: '100%' }}>
              <DataGrid
                rows={rows}
                columns={columns}
                initialState={{
                  pagination: {
                    paginationModel: { page: 0, pageSize: 5 },
                  },
                }}
                pageSizeOptions={[5, 10,15,20]}
                checkboxSelection
              />
            </div>
        </Container>
  );
}