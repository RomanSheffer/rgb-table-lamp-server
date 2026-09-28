package rgb.lamp.pet.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import rgb.lamp.pet.model.User;
import rgb.lamp.pet.repository.UserRepository;

@Slf4j
@Configuration
public class DefaultUserConfiguration {

    //создаем дефолтного пользователя для работы
    @org.springframework.beans.factory.annotation.Value("${DEF_PASS}")
    private String defaultPassword;

    @Bean
    public CommandLineRunner runner(UserRepository userRepository, PasswordEncoder encoder){

        return args -> {

            if(userRepository.findByUsername("admin").isEmpty()){

                User user = new User();
                user.setUsername("admin");
                user.setPassword(encoder.encode(defaultPassword));
                user.setRole("ADMIN");

                userRepository.save(user);
                log.info("создан дефолтный админ");
            }
        };
    }
}
