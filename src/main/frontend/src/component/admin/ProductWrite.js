import React,{ useState , useEffect , useRef } from 'react';
import axios from 'axios';

export default function ProductWrite( props ){

    const productForm = useRef(null);

    const onWriteHandler = () => {
        console.log('제품등록 함수');
        console.log( productForm.current )
        console.log( new FormData( productForm.current )  );
        let formData = new FormData( productForm.current );

        axios.post('/product' , formData ).then( r=>{
            console.log( r.data );
            if( r.data == true  ){alert("제품 등록성공");}
            else{ alert("제품 등록실패");}

        })

    }


    return(<>

        <form ref={ productForm }>
            제품명 : <input type="text" name="pname" />
            제품가격 : <input type="text" name="pprice"  />
            제품카테고리 : <input type="text" name="pcategory"  />
            제품설명 : <input type="text" name="pcommnet"  />
            제조사 : <input type="text" name="pmanufacturer"  />
            제품초기상태 : <input type="text" name="pstate" />
            제품재고 : <input type="text" name="pstock" />
            제품이미지 : <input type="file" name="pfiles" multiple />
            <button type="button" onClick={ onWriteHandler }>제품등록</button>
        </form>

    </>);
}