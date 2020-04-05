package reservation_module.repositories;

import reservation_module.models.Grave;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GraveRepository extends JpaRepository<Grave, Long> {
}
