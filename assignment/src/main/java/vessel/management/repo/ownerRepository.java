package vessel.management.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import vessel.management.data.owner;

@Repository
public interface ownerRepository extends JpaRepository<owner, Long> {
    Optional<owner>findById(Long ownerId);
}
