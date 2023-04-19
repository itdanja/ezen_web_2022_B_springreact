// p. 183 컴포넌트 만들기
import React from 'react';

export default function Todo(props) {
    return ( <>
        <div className="Todo">
            <input
                type="checkbox"
                id="todo0"
                name="todo0"
                value="todo0"
            />
            <label for="todo0"> Toto 컴포넌트 만들기 <label>
        </div>
    </>);

}