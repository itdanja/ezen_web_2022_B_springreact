package ezenweb.web.controller;

import ezenweb.web.domain.member.MemberDto;
import ezenweb.web.domain.todo.TodoDto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/todo")
@CrossOrigin(origins = "http://localhost:3000") // 해당 컨트롤러는 http://localhost:3000 요청 CORS 정책 적용
public class TodoController {
    @GetMapping("")
    public List<TodoDto> get( ){    // TodoDto , 서비스 , 리포지토리 , 엔티티  작업
        // 테스트 [ 서비스에게 전달 받은 리스트 라는 가정 ]
        /*
        List<TodoDto> list = new ArrayList<>();
        list.add( new TodoDto( 1 , "게시물1" , true ) );
        list.add( new TodoDto( 2 , "게시물2" , true ) );
        list.add( new TodoDto( 3 , "게시물3" , true ) );
        return list; // 서비스에서 리턴 결과를 axios에게 응답
        */
        // [과제] 서비스 구현
        return null;
    }
    @PostMapping("")
    public boolean post( @RequestBody TodoDto todoDto ){
        // [과제] 서비스 구현
        return true;  // 서비스에서 리턴 결과를 axios에게 응답
    }
    @PutMapping("")
    public boolean put( ){
        // [과제] 서비스 구현
        return true;   // 서비스에서 리턴 결과를 axios에게 응답
    }
    @DeleteMapping("")
    public boolean delete( @RequestParam int id ){
        // [과제] 서비스 구현
        return true;  // 서비스에서 리턴 결과를 axios에게 응답
    }
}

