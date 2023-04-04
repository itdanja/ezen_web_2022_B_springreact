package ezenweb.example.day02.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController // @ResponseBody + @Controller
@Slf4j
public class MappingController2 {

    @RequestMapping( value = "/red" , method = RequestMethod.GET )
    public String getRed(){
        log.info("클라이언트로부터 getRed 메소드 요청이 들어옴 ");
        return "정상 응답";
    }
    @RequestMapping( value = "/red" , method = RequestMethod.POST )
    public String postRed(){
        log.info("클라이언트로부터 postRed 메소드 요청이 들어옴");
        return  "정상 응답";
    }
    @RequestMapping( value = "/red" , method = RequestMethod.PUT )
    public String putRed(){
        log.info("클라이언트로부터 putRed 메소드 요청이 들어옴");
        return  "정상 응답";
    }

    @RequestMapping( value = "/red" , method = RequestMethod.DELETE )
    public String deleteRed(){
        log.info("클라이언트로부터 deleteRed 메소드 요청이 들어옴");
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
