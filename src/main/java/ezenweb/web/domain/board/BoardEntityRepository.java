package ezenweb.web.domain.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardEntityRepository extends JpaRepository< BoardEntity , Integer > {

    @Query(value = "select * from board " +
            "where  "+
            "IF( :cno = 0 , cno like '%%' , cno = :cno ) and " +
            "IF( :key = '' , true  , IF( :key = 'btitle' , btitle like %:keyword% , bcontent like %:keyword% ) ) "
            , nativeQuery = true ) // nativeQuery: 실제 해당 SQL 질의어 사용 뜻
    Page<BoardEntity> findBySearch( int cno , String key , String keyword , Pageable pageable);


}
