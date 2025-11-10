package br.com.thomas.foursales.infrastructure.controller;

import br.com.thomas.foursales.domain.response.AuthTokenResponse;
import br.com.thomas.foursales.infrastructure.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthTokenResponse> authentication(Authentication authentication) {

        var response = authService.authenticate(authentication);

        return ResponseEntity.ok(response);
    }
}
