import React , { useState , useEffect } from 'react';

// 1. 예제 훅[useState()]을 사용 안했을떄
    // return : 렌더링 될 HTML 반환 해주는 곳 [ 화면에 표시할 코드 ]
    // 컴포넌트 당 1번씩만 실행  didMount
/*
export default function Hook1(props){
    console.log('Hook1 컴포넌트 실행 ')
    let count = 0;
    const countHandler =  ()=>{
        alert(' countHandler 함수 실행');
        count++;
        alert('현재 count : ' + count );
    }
    return (<>
        <div>
            <p> 총 { count } 번 클릭했습니다 </p>
            <button onClick={ countHandler }>
                증가
            </button>
        </div>
    </>)
}
*/

// 2. 예제 훅[ useState() ] 을 사용 안했을떄
    // didMount -->
    // 상태변수 상태변경 : set~~() --> didupdate  재렌더링
/*
export default function Hook1(props){ console.log('Hook1 컴포넌트 실행 ')
    let [ count , setCount ] = useState( 0 );
    const countHandler =  ()=>{
        alert(' countHandler 함수 실행');
        setCount( count+1 );
    }
    return (<>
        <div>
            <p> 총 { count } 번 클릭했습니다 </p>
            <button onClick={ countHandler }>
                증가
            </button>
        </div>
    </>)
}
*/
//
export default function Hook1(props){ console.log('Hook1 컴포넌트 실행 ')
    let [ count , setCount ] = useState( 0 );
    // 1. useEffect() 함수
        /*
            컴포넌트의 생명주기[ Life Cycle ]
            mount : 생성 , update : 업데이트 , unmount : 제거
            1. useEffect( () => { } );
            2. mount , update 시 실행되는 함수
            3. 하나의 컴포넌트에서 여러번 사용 가능
            4. unmount 작동할경우 return 사용
        */
    useEffect( () => {
        console.log( " useEffect1() 실행 총 클릭수 : " + count );
    })
    useEffect( () => {
        console.log( " useEffect2() 실행 총 클릭수 : " + count );
    })
    useEffect( () => {
        console.log( " useEffect3() 실행 총 클릭수 : " + count );
        return () => { console.log('컴포넌트가 제거 되었을떄')} // unmount
    })

    const countHandler =  ()=>{  alert(' countHandler 함수 실행');
        setCount( count+1 );
    }
    return (<>
        <div>
            <p> 총 { count } 번 클릭했습니다 </p>
            <button onClick={ countHandler }>
                증가
            </button>
        </div>
    </>)
}