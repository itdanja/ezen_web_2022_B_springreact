package ezenweb.example.day03;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// 비지니스 로직(실직적인 업무) 담당
@Service    // MVC 서비스
@Slf4j
public class NoteService {
    @Autowired
    NoteEntityRepository noteEntityRepository;
    // 1. 쓰기
    public boolean write( NoteDto noteDto ){  log.info(" service write in " + noteDto ); // dto 안에는 nno X
        // 1. DTO --> 엔티티로 변환후 SAVE
        NoteEntity entity = noteEntityRepository.save( noteDto.toEntity() );
        if( entity.getNno() >= 0 ){ // 레코드가 생성 되었으면 [ 등록 성공 ]
            return true;
        }
        return false;
    }
    // 2. 출력
    public ArrayList<NoteDto> get( ) {log.info(" service get in ");
        // 1. 모든 엔티티 호출
        List<NoteEntity> entityList = noteEntityRepository.findAll();
        // 2. 모든 엔티티르 형변환 [ 엔티티-->DTO ]
        ArrayList<NoteDto> list = new ArrayList<>();
        entityList.forEach( e -> {
            list.add( e.toDto() );  // entity --> dto 변환후 리스트에 저장
        } );
        return list;
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
