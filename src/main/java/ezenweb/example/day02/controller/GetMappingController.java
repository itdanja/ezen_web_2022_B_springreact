package ezenweb.example.day02.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController // @ResponseBody + @Controller
@Slf4j
@RequestMapping("/get") // 공통 URL
public class GetMappingController {
    // --------------- 매개변수 요청 ----------------------- //
    // 1. HttpServletRequest request 객체를 이용한 매개변수 요청 [ JSP 주로 사용  ]
    @GetMapping("/method1")                     // 쿼리스트링 : http://localhost:8080/get/method1?param1=안녕
    public String method1( HttpServletRequest request ){
        String param = request.getParameter("param1");
        log.info("클라이언트로부터 받은 변수 : " + param );
        return "요청받은 데이터 : " +param;
    }
    // 2. @PathVariable : URL 경로상의 변수 요청
    @GetMapping("/method2/{param1}/{param2}")   // 경로상 :  http://localhost:8080/get/method2/안녕/하하
    public String method2( @PathVariable("param1") String param1 ,
                           @PathVariable("param2") String param2  ){
        log.info("클라이언트로부터 받은 변수 : " + param1 + "    " + param2 );
        return "요청받은 데이터 : " + param1 + "    " + param2;
    }
    // 3. @RequestParam
    @GetMapping("/method3") // http://localhost:8080/get/method3?param1=안녕&param2=하하
    public String method3( @RequestParam String param1 , @RequestParam String param2 ){
        log.info("클라이언트로부터 받은 변수 : " + param1 + "    " + param2 );
        return "요청받은 데이터 : " + param1 + "    " + param2 ;
    }   //  리턴타입 String :  Content-Type:	text/plain;charset=UTF-8

    // 4. @RequestParam
    @GetMapping("/method4") // http://localhost:8080/get/method3?param1=안녕&param2=하하
    public Map< String , String > method4( @RequestParam Map< String , String > params){
        log.info("클라이언트로부터 받은 변수 : " + params );
        return params;
    } //  리턴타입 Map/array :  Content-Type:	application/json

    // 5. get메소드에서 Dto로 매개변수 한번에 요청시 @RequestParam @RequestBody 사용불가
    @GetMapping("/method5") // http://localhost:8080/get/method3?param1=안녕&param2=하하
    public ParamDto method5( ParamDto dto ){
        log.info("클라이언트로부터 받은 변수 : " + dto );
        return dto;
    }  //  리턴타입  dto  :  Content-Type:	application/json

    // 6.
    @GetMapping("/method6") // http://localhost:8080/get/method3?param1=안녕&param2=하하
    public String method6( @ModelAttribute ParamDto dto ){
        log.info("클라이언트로부터 받은 변수 : " + dto );
        return "요청받은 데이터 : " + dto;
    }








}










