// Comment.js
    // class -> className
import React from 'react';
import styles from './Comment.css' // css 파일 가져오기
import logo from  '../../logo.svg'// img 파일 가져오기
export default function Comment( props ){
    return ( <>
        <div class="wrapper" >
            <div>
                <img src={ logo } class="logoImg"/>
            </div>
            <div class="contentContainer">
                <div class="nameText"> { props.name } </div>
                <div class="CommentText"> { props.comment } </div>
            </div>
        </div>
    </> );
}