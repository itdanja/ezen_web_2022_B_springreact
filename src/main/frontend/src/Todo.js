import React , { useState } from 'react';
import {
  ListItem,
  ListItemText,
  InputBase,
  Checkbox,
  ListItemSecondaryAction,
  IconButton,
} from "@mui/material"; // npm install @mui/material @emotion/react @emotion/styled
import DeleteOutlined from "@mui/icons-material/DeleteOutlined"; // npm install @mui/icons-material

export default function Todo( props ){

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





    return (
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
    );
};