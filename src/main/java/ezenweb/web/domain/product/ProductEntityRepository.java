package ezenweb.web.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductEntityRepository extends JpaRepository<ProductEntity , String> {
    // 상태가 0 인 제품만 출력
    @Query( value = "select * from product where pstate = 0" , nativeQuery = true )
    List<ProductEntity> findAllState();

}
