console.log("board.js")
// 1. 카테고리 등록
function setCategory(){  console.log("setCategory()")
    let cname = document.querySelector(".cname").value;
    $.ajax({ // ajax start
        url : "/board/category/write",
        method : "POST",
        data : JSON.stringify( {"cname" : cname} ),
        contentType : "application/json" ,
        success : (r)=>{
            console.log(r)
            if( r == true ) getCategory();
        } // success end
    }) // ajax end
} // setCategory end
// 2. 카테고리 모든 출력
getCategory()
function getCategory(){
    console.log("getCategory()")
    $.ajax({
        url : "/board/category/list",
        method : "get",
        success : (r)=>{
            console.log(r);
            let html = `<button onclick="selectCno(0)" type="button">전체보기</button>`;
            for( let cno in r  ){
                console.log(" 키/필드 : " + cno);
                console.log(" 키/필드 에 저장된 값  : " + r[cno] );
                html += `<button onclick="selectCno(${cno})" type="button">${ r[cno] }</button>`;
            } //for end
            document.querySelector('.categorylistbox').innerHTML = html;
        }
    })
}
// 3. 카테고리 선택
function selectCno( cno ){
    console.log( cno +" 의 카테고리 선택");
}


/*
    해당 변수의 자료형 확인 Prototype
    Array : forEach() 가능
        { object , object , object  }
    object : forEach() 불가능 --->  for( let key in object ){ } : 객체내 key를 하나씩 출력
        {
            필드명 : 값 ,
            필드명 : 값 ,
            필드명 : 값
        }
        object[필드명] : 해당 필드의 값 호출
*/
