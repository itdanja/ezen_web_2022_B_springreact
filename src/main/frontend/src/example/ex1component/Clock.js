import React from 'react';

export default function Clock( props ){
    // 함수안에서 js 문법은 자유적으로 작성가능
        //let clock = new Date().toLocaleTimeString();
    // 한수안에 return ( ) 안에서 js 문법은 { } 처리 = JSX 문법
    return ( <>
        <div>
            <h3> 리액트 시계 </h3>
            <h4>
                현재 시간 : { new Date().toLocaleTimeString() }
            </h4>
        </div>
    </>);
}
