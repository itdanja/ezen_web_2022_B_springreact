package ezenweb.web.controller;

import ezenweb.web.domain.board.BoardDto;
import ezenweb.web.domain.board.CategoryDto;
import ezenweb.web.domain.board.PageDto;
import ezenweb.web.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

// @CrossOrigin( origins = "http://localhost:3000")
@RestController
@Slf4j
@RequestMapping("/board")
public class BoardController {
    // 서비스 객체들
    @Autowired private BoardService boardService;

    // ---------------------- view 반환 ------------------------ //
/*    @GetMapping("")
    public Resource index(){
        return new ClassPathResource("templates/board/list.html");
    }*/
    // ----------------------- model 반환 ------------------------//
    // 1. 카테고리 등록
    @PostMapping("/category/write") // body { cname : '공지사항'  }
    public boolean categoryWrite( @RequestBody BoardDto boardDto ){
        log.info("c board dto : " + boardDto );
        boolean result = boardService.categoryWrite( boardDto );
        return result;
    }
    // 2. 카테고리 출력 [ 반환타입 :   { 1 : 공지사항 , 2 : 자유게시판 }
        // List : { 값 , 값 , 값 , 값 }     --> JSON[ array ]
        // Map : { 키 : 값 , 키 : 값 , 키 : 값 } ---> JSON [ object ]
    @GetMapping("/category/list")
    public List<CategoryDto> categoryList(  ){  log.info("c categoryList : " );
        List<CategoryDto> result = boardService.categoryList(  );
        return result;
    }


    // 3. 게시물 쓰기  // body { "btitle" : "입력한제목" , "bcontent" : "입력한내용" , "cno" : "선택받은번호" }
    @PostMapping("")  // 요청받은 JSON 필드명과 dto 필드명 일치할 경우 자동 매핑
    public byte write( @RequestBody BoardDto boardDto ){ log.info("c board dto : " + boardDto );
        byte result = boardService.write( boardDto );
        return result;
    }
    // 4. 카테고리별 게시물 전체 출력
    @GetMapping("")
    public PageDto get( PageDto pageDto ){
        PageDto result = boardService.list( pageDto );
        return result;
    }
    // 수정
    @PutMapping("")
    public boolean put(  ){  return true;  }
    // 삭제
    @DeleteMapping("")
    public boolean delete( @RequestParam int bno  ){
        boolean result = boardService.delete( bno );
        return result;
    }

    // 개별 출력
    @GetMapping("/getboard")
    public BoardDto getboard( @RequestParam int bno ){
        BoardDto result = boardService.getboard( bno) ;
        return result;
    }

    // 5. 내가 쓴 게시물 출력
    @GetMapping("/myboards")
    public List<BoardDto> myboards( ){ log.info("c myboards : " );
        List<BoardDto> result = boardService.myboards();
        return result;
    }

}
