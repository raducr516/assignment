package vessel.management.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vessel.management.data.ship;
import vessel.management.data.category;
import java.util.List;

@Repository
public interface shipRepository extends JpaRepository<ship, Long> {
    List<ship> findByCategory(category category);

}