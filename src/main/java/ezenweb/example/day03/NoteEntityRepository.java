package ezenweb.example.day03;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// 엔티티 조작 인터페이스
// extends JpaRepository< 엔티티명 , PK필드자료형 >
@Repository
public interface NoteEntityRepository
    extends JpaRepository< NoteEntity , Integer >
{

}
