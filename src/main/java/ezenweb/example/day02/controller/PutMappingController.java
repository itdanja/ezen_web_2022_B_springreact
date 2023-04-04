package ezenweb.example.day02.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/put")
public class PutMappingController {
    // post , put -> body [ @RequestBody ]
    @PutMapping("/method1")
    public ParamDto method1(@RequestBody ParamDto dto ){
        log.info( " put method1 : " + dto );
        return dto;
    }
    @PutMapping("/method2")
    public Map<String , String> method2(
            @RequestBody Map<String , String> map ){
        log.info( " put method2 : " + map );
        return map;
    }

}
