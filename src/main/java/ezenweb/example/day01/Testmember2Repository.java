package ezenweb.example.day01;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Testmember2Repository
        extends JpaRepository< Testmember2 , Integer > {
}
