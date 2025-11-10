package br.com.thomas.foursales.application.service;

import br.com.thomas.foursales.domain.entity.UserEntity;
import br.com.thomas.foursales.domain.enums.UserRoleEnum;
import br.com.thomas.foursales.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    private UserEntity mockUser;

    @BeforeEach
    void setUp() {
        mockUser = UserEntity.builder()
                .id(1L)
                .username("admin")
                .password("encodedPassword")
                .email("admin@test.com")
                .role(UserRoleEnum.ADMIN)
                .build();
    }

    @Test
    void whenLoadUserByUsername_thenReturnsUserDetails() {
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(mockUser));

        UserDetails result = userDetailsService.loadUserByUsername("admin");

        assertNotNull(result);
        assertEquals("admin", result.getUsername());
        assertEquals("encodedPassword", result.getPassword());
        assertTrue(result.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")));

        verify(userRepository, times(1)).findByUsername("admin");
    }

    @Test
    void whenLoadUserByUsernameWithInvalidUsername_thenThrowsUsernameNotFoundException() {
        String invalidUsername = "nonexistent";
        when(userRepository.findByUsername(invalidUsername)).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(invalidUsername));

        assertTrue(exception.getMessage().contains("Usuário não encontrado"));
        verify(userRepository, times(1)).findByUsername(invalidUsername);
    }
}
