package es.upm.miw.devops.code;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Component
public class UsersDatabase {

    private final List<User> users = new ArrayList<>();

    public UsersDatabase() {
        this.seed();
    }

    public void seed() {
        this.users.clear();

        List<Fraction> fractions1 = List.of(
                new Fraction(0, 1),
                new Fraction(1, 1),
                new Fraction(2, 1)
        );
        List<Fraction> fractions2 = List.of(
                new Fraction(2, 1),
                new Fraction(-1, 5),
                new Fraction(2, 4),
                new Fraction(4, 3)
        );
        List<Fraction> fractions3 = List.of(
                new Fraction(1, 5),
                new Fraction(3, -6),
                new Fraction(1, 2),
                new Fraction(4, 4)
        );

        // Usuario 1: BILLABLE (tiene todos los campos)
        User user1 = new User("1", "Oscar", "Fernandez", new ArrayList<>(fractions1));
        user1.setEmail("oscar@example.com");
        user1.setIdentity("12345678A");
        user1.setAddress("Calle Mayor 1");
        user1.setCity("Madrid");
        user1.setProvince("Madrid");
        user1.setPostalCode("28001");
        this.users.add(user1);

        // Usuario 2: BILLABLE (tiene todos los campos)
        User user2 = new User("2", "Ana", "Blanco", new ArrayList<>(fractions2));
        user2.setEmail("ana@example.com");
        user2.setIdentity("87654321B");
        user2.setAddress("Gran Via 5");
        user2.setCity("Valencia");
        user2.setProvince("Valencia");
        user2.setPostalCode("46001");
        this.users.add(user2);

        // Usuario 3: NO BILLABLE (faltan fields)
        User user3 = new User("3", "Oscar", "Ruiz", new ArrayList<>(fractions3));
        user3.setActive(false);
        this.users.add(user3);

        // Usuario 4: NO BILLABLE (postalCode está vacío)
        User user4 = new User("4", "Paula", "Torres", new ArrayList<>());
        user4.setEmail("paula@example.com");
        user4.setIdentity("11223344C");
        user4.setAddress("Plaza Sol 2");
        user4.setCity("Sevilla");
        user4.setProvince("Sevilla");
        user4.setPostalCode("   "); // EN BLANCO
        this.users.add(user4);

        // Usuario 5: NO BILLABLE (solo name fields)
        User user5 = new User("5", "Antonio", "Delgado", new ArrayList<>());
        this.users.add(user5);

        // Usuario 6: NO BILLABLE (sin campos)
        User user6 = new User("6", "Paula", "Villa", new ArrayList<>());
        this.users.add(user6);
    }

    public Stream<User> findAll() {
        return this.users.stream();
    }

    public Optional<User> findById(String id) {
        return this.users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    public void deleteById(String id) {
        this.users.removeIf(user -> user.getId().equals(id));
    }
}