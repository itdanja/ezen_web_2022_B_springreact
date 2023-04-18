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
        }
    })
}