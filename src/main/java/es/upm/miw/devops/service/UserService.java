package es.upm.miw.devops.service;

import es.upm.miw.devops.code.Role;
import es.upm.miw.devops.code.User;
import es.upm.miw.devops.repository.UserRepository;
import es.upm.miw.devops.rest.dto.UserActiveDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
        if (!active && user.hasRole(Role.ADMIN)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "ADMIN users cannot be deactivated");
        }
        user.setActive(active);
        this.userRepository.save(user);
    }

    public User update(String id, User newData) {
        User user = this.readById(id);
        user.setFirstName(newData.getFirstName());
        user.setFamilyName(newData.getFamilyName());
        user.setEmail(newData.getEmail());
        user.setIdentity(newData.getIdentity());
        user.setAddress(newData.getAddress());
        user.setCity(newData.getCity());
        user.setProvince(newData.getProvince());
        user.setPostalCode(newData.getPostalCode());
        return this.userRepository.save(user);
    }

    @Transactional
    public void updateActiveAll(List<UserActiveDto> updates) {
        updates.forEach(update -> {
            if (update.id() == null || update.active() == null) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Each item requires id and active");
            }
            this.updateActive(update.id(), update.active());
        });
    }
}