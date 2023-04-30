import React,{ useState , useEffect } from 'react';
import styles from '../../css/board/board.css';

export default function ReplyInput( props ) {
      const [comment, setComment] = useState('');

       const handleCommentChange = (event) => {
         setComment(event.target.value);
       };

       const handleAddComment = () => {
            props.onReplyWrite( comment , props.rindex );
            setComment('');
       };
  return <div>
          <div className="replyInput">
                <textarea value={comment} onChange={handleCommentChange} className="comment" />
                <button className="commentBtn" onClick={handleAddComment}>댓글작성</button>
          </div>
  </div>;
};


