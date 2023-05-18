import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';    //*1.import 이용해 App 컴포넌트[함수]를 불러온다.
import reportWebVitals from './reportWebVitals';
// ---------------------------------------------------------------- //

import Index from './component/Index';
//1. HTML에 존재하는 div 가져오기 [ document.getElementById('root') ]
//2. ReactDOM.createRoot( 해당 div ) : 해당 div를 리액트 root 로 사용하여 root 객체 생성
const root = ReactDOM.createRoot(document.getElementById('root'));
// ----- 수업용 컴포넌트
root.render(    <Index />   )

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
