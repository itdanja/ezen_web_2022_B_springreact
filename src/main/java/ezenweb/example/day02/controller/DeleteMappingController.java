package ezenweb.example.day02.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/delete")
public class DeleteMappingController {
    // post , put -> body   O [ @RequestBody -> 쿼리스트링 사용 X]
    // GET , delete -> body X [ @RequestParam -> 쿼리스트링 사용 O  ]
    @DeleteMapping("/method1")
    public ParamDto method1( ParamDto dto ){
        log.info( " put method1 : " + dto );
        return dto;
    }
    @DeleteMapping("/method2")
    public Map<String , String> method2(
            @RequestParam Map<String , String> map ){
        log.info( " put method2 : " + map );
        return map;
    }

}
