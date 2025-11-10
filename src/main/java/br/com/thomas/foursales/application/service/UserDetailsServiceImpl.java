package br.com.thomas.foursales.application.service;

import br.com.thomas.foursales.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("método=loadUserByUsername - carregando usuário {}", username);

        return userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.info("método=loadUserByUsername - usuário {} não encontrado", username);
                    return new UsernameNotFoundException("Usuário não encontrado: " + username);
                });
    }
}
