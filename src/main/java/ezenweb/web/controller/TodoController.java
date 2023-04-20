package ezenweb.web.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todo")
@CrossOrigin(origins = "http://localhost:3000") // 해당 컨트롤러는 http://localhost:3000 요청 CORS 정책 적용
public class TodoController {
    @GetMapping("")public String get(){ return "리액트 get 요청 안녕"; }
    @PostMapping("")public String post(){ return "리액트 post 요청 안녕"; }
    @PutMapping("")public String put(){ return "리액트 put 요청 안녕"; }
    @DeleteMapping("")public String delete(){ return "리액트 delete 요청 안녕"; }
}

