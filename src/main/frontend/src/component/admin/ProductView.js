import React,{ useState , useEffect , useRef } from 'react';
import axios from 'axios';

export default function ProductView( props ){

    //
    const [ row  , setRow ] = useState( {} );

    //
    useEffect( ()=>{ setRow( props.row ) } ,[props.row] )

    console.log( row )
    console.log( props.row )

    //
    const onUpdateHandler = () =>{

    }

    return(<>

        <form>
            제품명 : <input type="text" value={ row.pname} name="pname" />
            제품가격 : <input type="text" value={ row.pprice} name="pprice"  />
            제품카테고리 : <input type="text" value={ row.pcategory} name="pcategory"  />
            제품설명 : <input type="text" value={ row.pcommnet} name="pcommnet"  />
            제조사 : <input type="text" value={ row.pmanufacturer} name="pmanufacturer"  />
            제품초기상태 : <input type="text" value={ row.pstate} name="pstate" />
            제품재고 : <input type="text" value={ row.pstock} name="pstock" />
            제품이미지 : <input type="file"  name="pfiles" multiple />
            <button type="button" onClick={ onUpdateHandler }>제품수정</button>
        </form>

    </>)
}