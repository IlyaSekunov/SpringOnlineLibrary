package ru.ilya.spring_learning_library.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.ilya.spring_learning_library.model.User;
import ru.ilya.spring_learning_library.repository.UserRepository;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private User user;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void isUserExists_ReturnsFalseIfUserNotExists() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        assertThat(userDetailsService.isUserExists("Unknown user")).isFalse();
    }

    @Test
    void isUserExists_ReturnsTrueIfUserExists() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        assertThat(userDetailsService.isUserExists("Unknown user")).isTrue();
    }

    @Test
    void loadUserByUsername_ReturnsUserDetailsIfUserExists() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        assertThat(userDetailsService.loadUserByUsername("User")).isInstanceOf(UserDetails.class);
    }

    @Test
    void loadUserByUsername_ThrowUsernameNotFoundExceptionIfUserNotExists() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("Unknown User"));
    }
}
