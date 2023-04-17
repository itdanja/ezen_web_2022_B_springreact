package ezenweb.web.controller;

import ezenweb.web.domain.board.BoardDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/board")
public class BoardController {
    // 1. 카테고리 등록
    @PostMapping("/category/write")
    public boolean categoryWrite( @RequestBody BoardDto boardDto ){
        log.info("board dto : " + boardDto );
        return false;
    }
    // 2. 게시물 쓰기
    @PostMapping("/set")
    public boolean write( @RequestBody BoardDto boardDto ){
        log.info("board dto : " + boardDto );
        return false;
    }
    // 3. 내가 쓴 게시물 출력
    @GetMapping("/myboards")
    public List<BoardDto> myboards( ){
        log.info("myboards : " );
        return null;
    }

}
