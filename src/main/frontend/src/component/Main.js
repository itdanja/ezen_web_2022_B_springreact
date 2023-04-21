import React , { useState } from 'react';
export default function Main( props ) {

   let [ login , setLogin] = useState( JSON.parse( localStorage.getItem("login_token") ) );

   return (<div> 대문 입니다 { login.memail } </div>)
 }