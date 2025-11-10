package br.com.thomas.foursales.infrastructure.service;

import br.com.thomas.foursales.domain.response.AuthTokenResponse;
import org.springframework.security.core.Authentication;

public interface AuthService {

    AuthTokenResponse authenticate(Authentication authentication);
}
