package analytical_module.repositories;

import analytical_module.models.Fact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FactRepository extends JpaRepository<Fact, Long> {
}
