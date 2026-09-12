package es.upm.miw.devops.service;

import es.upm.miw.devops.code.User;
import es.upm.miw.devops.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

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
        when(this.userRepository.findById(userId)).thenReturn(Optional.of(user));

        this.userService.deleteById(userId);

        verify(this.userRepository).findById(userId);
        verify(this.userRepository).deleteById(userId);
    }

    @Test
    void testDeleteByIdWhenUserNotExists() {
        String userId = "999";
        when(this.userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> this.userService.deleteById(userId));
        verify(this.userRepository).findById(userId);
    }
}