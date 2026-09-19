package es.upm.miw.devops.service;

import es.upm.miw.devops.code.Role;
import es.upm.miw.devops.code.User;
import es.upm.miw.devops.repository.UserRepository;
import es.upm.miw.devops.rest.dto.UserActiveDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
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

    @Test
    void testUpdateActiveAdminDeactivateThrowsForbidden() {
        User admin = new User("6", "Paula", "Villa", new ArrayList<>());
        admin.addRole(Role.ADMIN);
        when(this.userRepository.findById("6")).thenReturn(Optional.of(admin));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> this.userService.updateActive("6", false));

        assertEquals(HttpStatus.FORBIDDEN, ex.getStatusCode());
        assertTrue(admin.isActive());
        verify(this.userRepository, never()).save(any());
    }

    @Test
    void testUpdateActiveAdminActivateAllowed() {
        User admin = new User("6", "Paula", "Villa", new ArrayList<>());
        admin.addRole(Role.ADMIN);
        admin.setActive(false);
        when(this.userRepository.findById("6")).thenReturn(Optional.of(admin));

        this.userService.updateActive("6", true);

        assertTrue(admin.isActive());
        verify(this.userRepository).save(admin);
    }

    @Test
    void testUpdateActiveCustomerDeactivateAllowed() {
        User customer = new User("2", "Ana", "Blanco", new ArrayList<>());
        customer.addRole(Role.CUSTOMER);
        when(this.userRepository.findById("2")).thenReturn(Optional.of(customer));

        this.userService.updateActive("2", false);

        assertFalse(customer.isActive());
        verify(this.userRepository).save(customer);
    }

    @Test
    void testUpdateActiveWithoutRolesDeactivateAllowed() {
        when(this.userRepository.findById("1")).thenReturn(Optional.of(user));

        this.userService.updateActive("1", false);

        assertFalse(user.isActive());
        verify(this.userRepository).save(user);
    }

    @Test
    void testUpdateWhenUserExists() {
        User existing = new User("1", "Oscar", "Fernandez", new ArrayList<>());
        User newData = new User("ignored", "Oscar", "Updated", new ArrayList<>());
        newData.setEmail("new@example.com");
        when(this.userRepository.findById("1")).thenReturn(Optional.of(existing));
        when(this.userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = this.userService.update("1", newData);

        assertEquals("1", result.getId());
        assertEquals("Updated", result.getFamilyName());
        assertEquals("new@example.com", result.getEmail());
        verify(this.userRepository).save(existing);
    }

    @Test
    void testUpdateWhenUserNotExists() {
        when(this.userRepository.findById("999")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> this.userService.update("999", new User()));
        verify(this.userRepository, never()).save(any());
    }

    @Test
    void testUpdateActiveAllUpdatesEveryUser() {
        User first = new User("1", "Oscar", "Fernandez", new ArrayList<>());
        User second = new User("3", "Oscar", "Ruiz", new ArrayList<>());
        second.setActive(false);
        when(this.userRepository.findById("1")).thenReturn(Optional.of(first));
        when(this.userRepository.findById("3")).thenReturn(Optional.of(second));

        this.userService.updateActiveAll(List.of(
                new UserActiveDto("1", false), new UserActiveDto("3", true)));

        assertFalse(first.isActive());
        assertTrue(second.isActive());
        verify(this.userRepository).save(first);
        verify(this.userRepository).save(second);
    }

    @Test
    void testUpdateActiveAllWhenUserNotExists() {
        when(this.userRepository.findById("999")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> this.userService.updateActiveAll(List.of(new UserActiveDto("999", true))));
    }

    @Test
    void testUpdateActiveAllWithMissingFields() {
        assertThrows(ResponseStatusException.class,
                () -> this.userService.updateActiveAll(List.of(new UserActiveDto("1", null))));
        assertThrows(ResponseStatusException.class,
                () -> this.userService.updateActiveAll(List.of(new UserActiveDto(null, true))));
    }
}