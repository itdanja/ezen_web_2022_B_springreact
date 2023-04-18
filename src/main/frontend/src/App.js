import logo from './logo.svg';
import './App.css';
import Todo from './Todo'
import AddTodo from './AddTodo'
import React , { useState } from 'react';
import {
  ListItem,
  ListItemText,
  InputBase,
  Checkbox,
  ListItemSecondaryAction,
  IconButton,
  Paper,
  List ,
  Container
} from "@mui/material";

function App() {

    const [ items , setItems ] = useState([]);

  const editItem = () => {
    setItems([...items]);
  };


       const deleteItem = (item) => {
         // 삭제할 아이템을 찾는다.
         const newItems = items.filter(e => e.id !== item.id);
         // 삭제할 아이템을 제외한 아이템을 다시 배열에 저장한다.
         setItems([...newItems]);
       }


       const addItem = (item) => {
         item.id = "ID-" + items.length; // key를 위한 id
         item.done = false; // done 초기화
         // 업데이트는 반드시 setItems로 하고 새 배열을 만들어 주어야 한다.
         setItems([...items, item]);
         console.log("items : ", items);
       };


    let todoItems =
        (
        <Paper style={{ margin : 16 }}>
            <List>
            {
                items.map( (i) => <Todo item={i} key={i.id}  deleteItem={deleteItem} editItem={editItem} />  )
            }
            </List>
        </Paper>
        );
    console.log( todoItems )
  return (
    <div className="App">
                <Container maxWidth="md">
                   <AddTodo addItem={addItem} />
                  <div className="TodoList">{todoItems}</div>
                </Container>
    </div>
  );
}

export default App;
