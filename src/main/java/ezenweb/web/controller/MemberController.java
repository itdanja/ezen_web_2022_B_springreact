package ezenweb.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController // @Controller + @ResponseBody
@Slf4j // 로그 기능 주입
@RequestMapping("/member")
public class MemberController {
    // 1. [C]회원가입
    @PostMapping("/info")   // URL 같아도 HTTP메소드 다르므로 식별가능
    public boolean write( @RequestBody MemberDto memberDto ){ // JAVA 클래스내 메소드 이름은 중복 불가능
        return false;
    }
    // 2. [R]회원정보 호출
    @GetMapping("/info")
    public MemberDto info( @RequestParam int mno ){
        return null;
    }
    // 3. [U]회원정보 수정
    @PutMapping("/info")
    public boolean update( @RequestBody MemberDto memberDto ){
        return false;
    }
    // 4. [D]회원정보 탈퇴
    @DeleteMapping("/info")
    public boolean delete( @RequestParam int mno ){
        return false;
    }

}
