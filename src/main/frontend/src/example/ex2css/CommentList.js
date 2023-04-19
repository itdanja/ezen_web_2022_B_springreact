// CommentList.js
import React from 'react';
import Comment from './Comment';
export default function CommentList( props ){

    // Ajax 이용한 서버로 부터 응답 받은 데이터[JSON] 예시
    let r = [
        { name : "유재석" , "comment" : "안녕하세요" },
        { name : "강호동" , "comment" : "그래 안녕" },
        { name : "신동엽" , "comment" : "나도 안녕" }
    ];
    console.log( r );
    // return 안에서 js 문 사용시 {  } => jsx문법
    // jsx 주석 => {/*  주석 */}
    // map [ return 가능 ] vs forEach  [ return 불가능 ]
    return ( <>
        <div>
            {
            /* jsx 시작 */
                r.map( (c)=>{
                    return ( <Comment name={ c.name } comment={ c.comment } /> ) ;
                })
            /* jsx 끝 */
            }
        </div>
    </> );
}