console.log("js열림");
// 1. 등록 [ JSON.stringify() : json타입에서 문자열 타입으로 변환  , JSON.parse : 문자열타입에서 json타입으로 변환  ]
function onwrite(){ console.log(" onwrite 실행");
    $.ajax({ // AJAX 이용한 @PostMapping 에게 요청응답
        url: "/note/write", // 매핑 주소값
        method : "post",    // 매핑 HTTP 메소드
        // body 값에 JSON형식의 문자열타입 // contentType : "application/json"
        data : JSON.stringify( { "ncontents" : document.querySelector(".ncontents").value } ) ,
        contentType : "application/json" ,
        success : (r) =>{
            console.log(r)
            if( r == true ){
                alert('글쓰기 성공'); onget();
                document.querySelector(".ncontents").value = '';
            }
            else{ alert('글쓰기 실패 ')}
        }
    });
} // end
// 2. 등록된 글 목록 호출
onget();
function onget(){
    $.ajax({ // AJAX 이용한 @GetMapping 에게 요청응답
        url : "/note/get",
        method : "get" ,
        success : (r) => { console.log(r) }
    })
}
