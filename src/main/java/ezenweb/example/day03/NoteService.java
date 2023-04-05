package ezenweb.example.day03;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

// 비지니스 로직(실직적인 업무) 담당
@Service    // MVC 서비스
@Slf4j
public class NoteService {

    // 1. 쓰기
    public boolean write( NoteDto noteDto ){
        log.info(" service write in " + noteDto );
        return true;
    }
    // 2. 출력
    public ArrayList<NoteDto> get( ) {
        log.info(" service get in ");
        return null;
    }
    // 3. 삭제
    public boolean delete( int nno ){
        log.info(" service delete in ");
        return true;
    }
    // 4. 수정
    public boolean update( NoteDto noteDto ){
        log.info(" service update in ");
        return true;
    }
}
