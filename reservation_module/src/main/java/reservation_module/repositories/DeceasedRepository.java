package reservation_module.repositories;

import reservation_module.models.Deceased;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeceasedRepository extends JpaRepository<Deceased, Long> {
}
