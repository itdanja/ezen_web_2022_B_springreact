import axios from 'axios'; // 터미널 npm i axios

export default function Index( props ){

    axios.get("http://localhost:8080/board/list" , { params: { cno : 0 } } ).then( r => { console.log( r ); } ) .catch( err => { console.log( err); } )


     return  (
            <div className="webbox">
                <h3> 이런게 리액트 군요 </h3>

            </div>
        );
}