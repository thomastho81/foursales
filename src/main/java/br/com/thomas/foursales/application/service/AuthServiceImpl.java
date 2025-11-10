package br.com.thomas.foursales.application.service;

import br.com.thomas.foursales.domain.entity.UserEntity;
import br.com.thomas.foursales.domain.response.AuthTokenResponse;
import br.com.thomas.foursales.infrastructure.security.JwtTokenGenerator;
import br.com.thomas.foursales.infrastructure.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtTokenGenerator jwtTokenGenerator;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Override
    public AuthTokenResponse authenticate(Authentication authentication) {
        UserEntity user = (UserEntity) authentication.getPrincipal();
        String token = jwtTokenGenerator.generateToken(authentication);

        return AuthTokenResponse.builder()
                .token(token)
                .username(user.getUsername())
                .role(user.getRole().getRole())
                .expiresIn(expiration)
                .build();
    }
}
