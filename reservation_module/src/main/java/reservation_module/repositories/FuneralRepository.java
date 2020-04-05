package reservation_module.repositories;

import reservation_module.models.Funeral;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuneralRepository extends JpaRepository<Funeral, Long> {
}
