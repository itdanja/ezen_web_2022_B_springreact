// p. 183 컴포넌트 만들기
import React , { useState } from 'react';
import axios from 'axios'; // 터미널 npm i axios
/*
    npm install @mui/material @emotion/react @emotion/styled
    npm install @mui/material @mui/styled-engine-sc styled-components
*/
import { ListItem , ListItemText , InputBase , Checkbox ,

ListItemSecondaryAction ,
IconButton


} from '@mui/material';
import DeleteOutlined from "@mui/icons-material/DeleteOutlined"; // npm install @mui/icons-material

export default function Todo(props) {
    console.log( props )
    // 1. Hook 상태관리 useState 함수
    const [ item , setItem ] = useState( props.item );

const deleteItem = props.deleteItem;
     const [readOnly, setReadOnly] = useState(true);
       const editItem = props.editItem;

              // deleteEventHandler 작성
              const deleteEventHandler = () => {
                deleteItem(item);
              };

               const turnOffReadOnly = () => {
                  setReadOnly(false);
                }

                  // turnOnReadOnly 함수 작성
                  const turnOnReadOnly = (e) => {
                    if (e.key === "Enter") {
                        setReadOnly(true);
                        axios.put("http://localhost:8080/todo/put" , item ).then( r => { console.log( r ); } )

                    }
                  };

                    const editEventHandler = (e) => {
                      item.title = e.target.value;

                      editItem();
                    };

                      const checkboxEventHandler = (e) => {
                        item.done = e.target.checked;
                        editItem();
                      }



    return ( <>
        <ListItem>
            <Checkbox checked={item.done}
                              onChange={checkboxEventHandler} />
            <ListItemText>
                <InputBase
                inputProps={{
                              "aria-label": "naked",
                              readOnly: readOnly }}
                          onClick={turnOffReadOnly}
                          onKeyDown={turnOnReadOnly}
                           onChange={editEventHandler}
                          type="text"
                          id={item.id}
                          name={item.id}
                          value={item.title}
                          multiline={true}
                          fullWidth={true}
                />
            </ListItemText>

                           <ListItemSecondaryAction>
                                <IconButton aria-label="Delete Todo"
                                  onClick={deleteEventHandler} >
                                  <DeleteOutlined />
                                </IconButton>
                              </ListItemSecondaryAction>

        </ListItem>
    </>);
}