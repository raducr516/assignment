package vessel.management.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vessel.management.data.category;

@Repository
public interface categoryRepository extends JpaRepository<category, Long> {

}