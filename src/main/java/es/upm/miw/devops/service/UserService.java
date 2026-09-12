package es.upm.miw.devops.service;

import es.upm.miw.devops.code.User;
import es.upm.miw.devops.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User readById(String id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Non existent user with id: " + id));
    }

    public List<User> search(Boolean billable) {
        return this.userRepository.findAll().stream()
                .filter(user -> billable == null || billable == user.isBillable())
                .toList();
    }

    public void deleteById(String id) {
        this.readById(id);
        this.userRepository.deleteById(id);
    }

    public void updateActive(String id, boolean active) {
        User user = this.readById(id);
        user.setActive(active);
        this.userRepository.save(user);
    }
}