import React , { useState , useEffect } from 'react'
import axios from 'axios'
export default function Signup( props ) {
    return(<>
                <h3> 회원가입 페이지 </h3>
                <form>
                    아이디[이메일] : <input type="text" className="memail" /> <br/>
                    비밀번호 : <input type="text" className="mpassword" />  <br/>
                    전화번호 : <input type="text" className="mname" />  <br/>
                    이메일 :  <input type="text" className="mphone" />  <br/>
                    <button onclick="onSignup()" type="button"> 가입 </button>
                </form>
    </>)
}