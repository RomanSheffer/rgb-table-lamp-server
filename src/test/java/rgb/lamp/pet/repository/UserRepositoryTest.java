package rgb.lamp.pet.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import rgb.lamp.pet.model.User;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("тест сохранения и загрузки из БД")

    void saveAndLoadFromDB( ) {

        //arrange
        User user = new User();
        user.setUsername("тест юзер");
        user.setPassword("123");
        user.setRole("Tester");

        // act
        userRepository.save(user);
        User foundUser = userRepository.findByUsername("тест юзер")
                .orElseThrow(() -> new UsernameNotFoundException("пользователь не найден"));

        // assert
        assertEquals(user.getUsername(), foundUser.getUsername());
        assertEquals(user.getPassword(), foundUser.getPassword());
        assertEquals(user.getRole(), foundUser.getRole());
    }
}