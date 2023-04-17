

function setCategory(){

    let cname = document.querySelector('.cname').value;

    $.ajax({
        url : "/board/category/write" ,
        method : 'POST',
        data : JSON.stringify({ "cname" : cname} ),
        contentType : 'application/json',
        success : (r)=>{
            console.log(r);
            getCategory();
        }
    })

}

getCategory()
function getCategory(){

    $.ajax({
        url : "/board/category/list",
        method : "get",
        success : (r)=>{
            console.log(r);
            let html = '';
            for( let cno in r ){
                html +=`<button onclick="selectCategory(${cno})" type="button">${ r[cno]}</button>`
            }
            document.querySelector('.clistbox').innerHTML = html;
        }
    })
}

let selectCno = 0;
function selectCategory( cno ){
    console.log( cno +" 카테고리 선택 ")
    selectCno = cno;
    getBoard();
}

function setBoard(){
    let btitle = document.querySelector('.btitle').value;
    let bcontent = document.querySelector('.bcontent').value;

    $.ajax({
        url : "/board/write" ,
        method : 'POST',
        data : JSON.stringify({ "btitle" : btitle , "bcontent":  bcontent , "cno" : selectCno } ),
        contentType : 'application/json',
        success : (r)=>{
            console.log(r);
        }
    })
}
getBoard();
function getBoard(){
    $.ajax({
        url : "/board/list" ,
        method : 'get',
        data : { "cno" : selectCno },
        success : (r)=>{
            console.log(r);
            let html = '<tr>  <th> 번호 </th> <th> 제목 </th> <th> 작성자 </th></tr>';
            r.forEach( (b) => {
                html +=
                '<tr>  <td> '+b.bno+' </td> <td onclick="getview('+b.bno+')">'+b.btitle+' </td> <td>  </td></tr>';
            })
            document.querySelector(".blistbox").innerHTML = html;

        }
    })
}

myboards();
function myboards(){
    $.ajax({
        url : "/board/myboards" ,
        method : 'get',
        success : (r)=>{
            console.log(r);
        }
    })
}
function getview( bno ){
    // 1. 클릭한 게시물번호 저장
    sessionStorage.setItem("bno", bno );
    // 2. 페이지 전환
    location.href="/board/view";
}







