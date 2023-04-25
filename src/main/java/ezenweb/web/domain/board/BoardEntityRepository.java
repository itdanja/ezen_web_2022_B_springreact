package ezenweb.web.domain.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardEntityRepository extends JpaRepository< BoardEntity , Integer > {

    // JPA 형식이 아닌 순수SQL 적용하는 함수 정의
        // 동일한 cno를 찾기
        // [JSP] select * from board where cno = ?
            // ps.setInt( 1 , cno );
        // [JPA] select * from board where cno = :cno
            // :매개변수명 ( 해당 함수의 매개변수 이름 )

    // cno 가 0 이면 cno가 0인경우 조건이 존재하지 않는다.
    // cno 가 0 이면 조건이 없어야 한다. [ if( 조건 , 참 , 거짓 ) ]
    //
    @Query( value = " select * from board " +
                    " where if( :cno = 0 , cno like '%%'  , cno = :cno ) "
            , nativeQuery = true )
    Page<BoardEntity> findBySearch(  int cno , Pageable pageable );


}
