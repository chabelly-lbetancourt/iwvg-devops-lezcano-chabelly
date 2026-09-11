package es.upm.miw.devops.rest;

import es.upm.miw.devops.code.User;
import es.upm.miw.devops.service.UserService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(UserResource.USERS)
public class UserResource {

    public static final String USERS = "/user";
    public static final String ID_ID = "/{id}";
    public static final String ACTIVE = "/{id}/active";

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(ID_ID)
    public User readById(@PathVariable String id) {
        return this.userService.readById(id);
    }

    @GetMapping
    public List<User> search(@RequestParam(required = false) Boolean billable) {
        return this.userService.search(billable);
    }

    @DeleteMapping(ID_ID)
    public void deleteById(@PathVariable String id) {
        this.userService.deleteById(id);
    }

    @PutMapping(ACTIVE)
    public void updateActive(@PathVariable String id, @RequestParam boolean active) {
        this.userService.updateActive(id, active);
    }
}