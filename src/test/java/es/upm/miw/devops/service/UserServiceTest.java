package es.upm.miw.devops.service;

import es.upm.miw.devops.code.User;
import es.upm.miw.devops.code.UsersDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private UsersDatabase usersDatabase;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User("1", "Oscar", "Fernandez", List.of());
    }

    @Test
    void testDeleteByIdWhenUserExists() {
        String userId = "1";
        when(this.usersDatabase.findById(userId)).thenReturn(Optional.of(user));

        this.userService.deleteById(userId);

        verify(this.usersDatabase).findById(userId);
        verify(this.usersDatabase).deleteById(userId);
    }

    @Test
    void testDeleteByIdWhenUserNotExists() {
        String userId = "999";
        when(this.usersDatabase.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> this.userService.deleteById(userId));
        verify(this.usersDatabase).findById(userId);
    }
}