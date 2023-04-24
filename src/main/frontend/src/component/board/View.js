import React,{ useState , useEffect  } from 'react';
import { useParams  } from "react-router-dom";
import axios from 'axios';

export default function View( props ) {

    const params = useParams(); //  useParams() 훅 : 경로[URL]상의 매개변수 가져올때

    const [ board , setBoard ] = useState( { } ); // *게시물 메모리
    useEffect( // 1. 서버로 부터 해당 게시물번호의 게시물정보 -> useState[board]  요청
        ()=>axios.get( "/board/getBoard" , { params : { bno: params.bno } } )
        .then( res => { setBoard( res.data ) } )
    , [] )

    return(<>
        <div>
            <div> { board.btitle }</div>
            <div>{ board.bcontent }</div>
        </div>
    </>)
}