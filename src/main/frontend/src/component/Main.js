import React , { useState , useEffect } from 'react';
import axios from 'axios';
export default function Main( props ) {
   const [ items , setItems ] = useState([]);
   useEffect( () =>{
        axios.get( '/product/main' ).then( r=>{ setItems( r.data ) })
   } , [])
   console.log( items );
   return (<div>
        {
            items.map( item => {
                return(<div>
                        <img src={ 'http://localhost:8080/static/media/'+item.files[0].uuidFile} />
                        <div>{ item.pname }</div>
                </div>)
            })
        }
   </div>)
 }