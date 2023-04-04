package ezenweb.example.day02.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController // @ResponseBody + @Controller
@Slf4j
@RequestMapping("/color")   // 해당 클래스의 URL 매핑 [ 해당클래스에 정의된 메소드들의 공통 URL ]
public class MappingController4 {

    @GetMapping( "/pink" )  // http://localhost:8080/color/pink
    public String getPink(){
        log.info("클라이언트로부터 getPink 메소드 요청이 들어옴 ");
        return "정상 응답";
    }
    @PostMapping( "/pink"  )
    public String postPink(){
        log.info("클라이언트로부터 postPink 메소드 요청이 들어옴");
        return  "정상 응답";
    }
    @PutMapping("/pink"  )
    public String putPink(){
        log.info("클라이언트로부터 putPink 메소드 요청이 들어옴");
        return  "정상 응답";
    }
    @DeleteMapping( "/pink"  )
    public String deletePink(){
        log.info("클라이언트로부터 deletePink 메소드 요청이 들어옴");
        return  "정상 응답";
    }
}
/*
            스프링 부트 동작 구조



    크롬/ajax/js ----------요청----------->     서블릿 컨테이너    -------> Dispatcher Servlet
             http://localhost:8080/orange                                   핸들러 매핑으로 해당 컨트롤러( 스프링 컨테이너 ) 검색
                                                                ------->  Mapping 검색
                                                                           @RequestMapping( value = "/orange" , method = RequestMethod.GET )
                <--------------------------응답----------------------------





 */
