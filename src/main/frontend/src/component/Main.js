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
                        <img src={ 'http://ec2-13-125-115-184.ap-northeast-2.compute.amazonaws.com:8080/static/media/'+item.files[0].uuidFile} />
                        <div>{ item.pname }</div>
                </div>)
            })
        }
   </div>)
 }