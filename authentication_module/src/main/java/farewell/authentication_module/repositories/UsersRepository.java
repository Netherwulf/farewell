package farewell.authentication_module.repositories;

import farewell.authentication_module.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface UsersRepository extends JpaRepository<User, Long> {

    User findByEmail(String username);
}
