package ezenweb.web.controller;

import ezenweb.web.domain.todo.TodoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j // 로그 기능 주입
@RestController // @Controller + @ResponseBody
@RequestMapping("/todo")
public class TodoController {

    @GetMapping("/get")
    public List<TodoDto> get (){ log.info( "get");

        List<TodoDto> result = new ArrayList<>();
        result.add( new TodoDto("1" , "내용1") );
        result.add( new TodoDto("2" , "내용2"));
        result.add( new TodoDto("3" , "내용3"));

        return result;
    }

    @PostMapping("/post")
    public boolean post ( @RequestBody TodoDto item){ log.info( "post : " + item);
        return true;
    }


    @PutMapping("/put")
    public boolean put ( @RequestBody TodoDto item ){ log.info( "put : " + item );
        return true;
    }


    @DeleteMapping("/delete")
    public boolean delete ( @RequestParam int id ){ log.info( "delete : " + id );
        return true;
    }

}

