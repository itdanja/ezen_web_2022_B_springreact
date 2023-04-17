package ezenweb.web.controller;

import ezenweb.web.domain.board.BoardDto;
import ezenweb.web.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/board")
@CrossOrigin(origins = "http://localhost:3000") // 리액트와 연결하기 위한 리액트 포트번호
public class BoardController {
    @Autowired private BoardService boardService;

    @GetMapping("")
    public Resource index(){
        return new ClassPathResource("templates/board/list.html");
    }
    @GetMapping("/write")
    public Resource write(){
        return new ClassPathResource("templates/board/write.html");
    }

    @GetMapping("/view")
    public Resource view(){
        return new ClassPathResource("templates/board/view.html");
    }


    // 1. 카테고리 등록
    @PostMapping("/category/write")
    public boolean categoryWrite( @RequestBody BoardDto boardDto ){
        log.info("c board dto : " + boardDto );
        boolean result = boardService.categoryWrite( boardDto );
        return result;
    }
    @GetMapping("/category/list")
    public Map<Integer , String> categoryList(  ){
        log.info("c board dto : "   );
        Map<Integer , String> result = boardService.categoryList(  );
        return result;
    }

    // 2. 게시물 쓰기
    @PostMapping("/write")
    public boolean write( @RequestBody BoardDto boardDto ){
        log.info("c board dto : " + boardDto );
        boolean result = boardService.write( boardDto );
        return result;
    }
    @GetMapping("/list")
    public ArrayList<BoardDto> list(@RequestParam int cno ){
        log.info("c board dto : " + cno );
        ArrayList<BoardDto> result = boardService.list( cno );
        return result;
    }


    @GetMapping("/getview")
    public BoardDto getview(@RequestParam int bno ){
        log.info("c board dto : " + bno );
        BoardDto result = boardService.view( bno );
        return result;
    }

    // 3. 내가 쓴 게시물 출력
    @GetMapping("/myboards")
    public List<BoardDto> myboards( ){
        log.info("c myboards : " );
        List<BoardDto> result = boardService.myboards();
        return result;
    }




}
