import React , { useState , useEffect } from 'react'
import axios from 'axios'
export default function Signup( props ) {
    //function onSignup(){} --> 변수형 익명함수 변환
    //unction onSignup(){} --> const 변수 = () => {}
    const onSignup = () =>{ console.log( 'onSignup open' )
        let info = {
            memail : document.querySelector(".memail").value,
            mpassword : document.querySelector(".mpassword").value,
            mname : document.querySelector(".mname").value,
            mphone : document.querySelector(".mphone").value
        }
        console.log( info )
    // ajax ---> axios 변환
        axios
        .post("/member/info" , info )
        .then( r => {  console.log( r );
            if( r.data == true  ){
                alert('회원가입 성공');window.location.href="/member/login"; // window.location.href="이동할 경로";
            }else{
                alert('회원가입 실패 [ 관리자에게 문의 ]');
            }
        })
        .catch( err => { console.log( err ) });
    }

    // 2. 아이디 중복체크
    let [ memailMsg , setMemailMsg ] = useState('');
    const idCheck = (e) => {  // 1. console.log( document.querySelector('.memail').value ) ;  // 2. console.log( e.target.value ) ;
        axios.get( "/member/idcheck" , { params : { memail : e.target.value } } )
            .then( res => {
                if( res.data == true ){ setMemailMsg('사용중인 이메일 입니다.')}
                else{ setMemailMsg('사용 가능 한 이메일 입니다. ')}
                }
            )  .catch( e => console.log( e ) )
    }
    return(<div>
                <h3> 회원가입 페이지 </h3>
                <form>
                    아이디[이메일] : <input type="text" className="memail" onChange={ idCheck } />
                    <span> { memailMsg } </span>
                    <br/>
                    비밀번호 : <input type="text" className="mpassword" />  <br/>
                    전화번호 : <input type="text" className="mname" />  <br/>
                    이메일 :  <input type="text" className="mphone" />  <br/>
                    <button onClick={ onSignup } type="button"> 가입 </button>
                </form>
    </div>)
}
/*
    HTML ---> JSX
        1. <> </>
        2. class -> clasName
        3. style -> style={{ }}
        4. 카멜표기법 :
            onclick -> onClick
            margin-top -> marginTop
*/
