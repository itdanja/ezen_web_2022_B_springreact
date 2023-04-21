import React from 'react';
import
    { BrowserRouter , Routes , Route }
    from "react-router-dom" // npm install react-router-dom
import Login from "./member/Login"

export default function Index( props ) {
    return ( <>
        <BrowserRouter>
            <Routes>
                <Route path="login" element = { <Login/> } />
            </Routes>
        </BrowserRouter>
    </> )
}


