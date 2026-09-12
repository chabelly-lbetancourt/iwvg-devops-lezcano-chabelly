package es.upm.miw.devops.config;

import es.upm.miw.devops.code.User;
import es.upm.miw.devops.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initializeDatabase(UserRepository userRepository) {
        return args -> {
            if (userRepository.count() == 0) {
                User user1 = new User("1", "Oscar", "Fernandez", new ArrayList<>());
                user1.setEmail("oscar@example.com");
                user1.setIdentity("12345678A");
                user1.setAddress("Calle Mayor 1");
                user1.setCity("Madrid");
                user1.setProvince("Madrid");
                user1.setPostalCode("28001");
                userRepository.save(user1);

                User user2 = new User("2", "Ana", "Blanco", new ArrayList<>());
                user2.setEmail("ana@example.com");
                user2.setIdentity("87654321B");
                user2.setAddress("Gran Via 5");
                user2.setCity("Valencia");
                user2.setProvince("Valencia");
                user2.setPostalCode("46001");
                userRepository.save(user2);

                User user3 = new User("3", "Oscar", "Ruiz", new ArrayList<>());
                user3.setActive(false);
                userRepository.save(user3);

                User user4 = new User("4", "Paula", "Torres", new ArrayList<>());
                user4.setEmail("paula@example.com");
                user4.setIdentity("11223344C");
                user4.setAddress("Plaza Sol 2");
                user4.setCity("Sevilla");
                user4.setProvince("Sevilla");
                user4.setPostalCode("   ");
                userRepository.save(user4);

                User user5 = new User("5", "Antonio", "Delgado", new ArrayList<>());
                userRepository.save(user5);

                User user6 = new User("6", "Paula", "Villa", new ArrayList<>());
                userRepository.save(user6);
            }
        };
    }
}