package rgb.lamp.pet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rgb.lamp.pet.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    String username(String username);
}
