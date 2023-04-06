package ezenweb.example.day04.domain.entity.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // 엔티티 조작 인터페이스
public interface ProductEntityRepository  extends JpaRepository< ProductEntity , Integer > {

}
