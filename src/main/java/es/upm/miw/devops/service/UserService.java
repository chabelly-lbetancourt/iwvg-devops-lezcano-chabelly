package es.upm.miw.devops.service;

import es.upm.miw.devops.code.User;
import es.upm.miw.devops.code.UsersDatabase;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private final UsersDatabase usersDatabase;

    public UserService(UsersDatabase usersDatabase) {
        this.usersDatabase = usersDatabase;
    }

    public User readById(String id) {
        return this.usersDatabase.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Non existent user with id: " + id));
    }
}