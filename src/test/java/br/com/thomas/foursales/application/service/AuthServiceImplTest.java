package br.com.thomas.foursales.application.service;

import br.com.thomas.foursales.domain.entity.UserEntity;
import br.com.thomas.foursales.domain.enums.UserRoleEnum;
import br.com.thomas.foursales.domain.response.AuthTokenResponse;
import br.com.thomas.foursales.infrastructure.security.JwtTokenGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private JwtTokenGenerator jwtTokenGenerator;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthServiceImpl authService;

    private UserEntity mockUser;
    private static final String MOCK_TOKEN = "mock.jwt.token";
    private static final Long EXPIRATION = 1800L;

    @BeforeEach
    void setUp() {
        mockUser = UserEntity.builder()
                .id(1L)
                .username("admin")
                .email("admin@test.com")
                .role(UserRoleEnum.ADMIN)
                .build();

        ReflectionTestUtils.setField(authService, "expiration", EXPIRATION);
    }

    @Test
    void whenAuthenticate_thenReturnsAuthTokenResponse() {
        when(authentication.getPrincipal()).thenReturn(mockUser);
        when(jwtTokenGenerator.generateToken(any(Authentication.class))).thenReturn(MOCK_TOKEN);

        AuthTokenResponse response = authService.authenticate(authentication);

        assertNotNull(response);
        assertEquals(MOCK_TOKEN, response.getToken());
        assertEquals("admin", response.getUsername());
        assertEquals("ROLE_ADMIN", response.getRole());
        assertEquals(EXPIRATION, response.getExpiresIn());

        verify(authentication, times(1)).getPrincipal();
        verify(jwtTokenGenerator, times(1)).generateToken(authentication);
    }
}
