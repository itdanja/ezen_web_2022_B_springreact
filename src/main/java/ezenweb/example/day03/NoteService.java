package ezenweb.example.day03;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// 비지니스 로직(실직적인 업무) 담당
@Service    // MVC 서비스
@Slf4j
public class NoteService {
    @Autowired
    NoteEntityRepository noteEntityRepository;
    // 1. 쓰기
    @Transactional
    public boolean write( NoteDto noteDto ){  log.info(" service write in " + noteDto ); // dto 안에는 nno X
        // 1. DTO --> 엔티티로 변환후 SAVE
        NoteEntity entity = noteEntityRepository.save( noteDto.toEntity() );
        if( entity.getNno() >= 0 ){ // 레코드가 생성 되었으면 [ 등록 성공 ]
            return true;
        }
        return false;
    }
    // 2. 출력
    @Transactional
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
    @Transactional
    public boolean delete( int nno ){    log.info(" service delete in ");
        // 1. 삭제할 식별번호[pk]를 이용한 엔티티 검색 [ 검색성공시:엔티티 / 검색실패시 : null ]
        Optional<NoteEntity> optionalNoteEntity = noteEntityRepository.findById( nno );
        // 2. 포장클래스 <엔티티>
        if( optionalNoteEntity.isPresent() ){ // 포장 클래스내 엔티티가 들어있으면
            NoteEntity noteEntity = optionalNoteEntity.get(); // 엔티티 꺼내기
            noteEntityRepository.delete( noteEntity ); // 찾은 엔티티를 리포지토리 통해 삭제하기
            return true;
        }
        return false;
    }
    // 4. 수정
    // @Transactional : 해당 메소드내 엔티티객체 필드의 변화가 있을경우 실시간으로 commit 처리
    @Transactional // import javax.transaction.Transactional
    public boolean update( NoteDto noteDto ){    log.info(" service update in ");
        // 1. 수정할 pk번호 를 이용한 엔티티 검색
        Optional<NoteEntity> optionalNoteEntity = noteEntityRepository.findById( noteDto.getNno() );
        // 2. 포장클래스<엔티티>
        if(optionalNoteEntity.isPresent()) {// 포장클래스내 엔티티가 존재하면
            NoteEntity noteEntity = optionalNoteEntity.get();// 엔티티 꺼내기
            // 3. 객체내 필드 값 변경 --> 엔티티객체 필드 값 변경 --> 해당 레코드의 필드 값 변경
            noteEntity.setNconents( noteDto.getNcontents() );
            return true;
        }
        return false;
    }
}
