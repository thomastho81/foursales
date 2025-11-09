package br.com.thomas.foursales.infrastructure.service;

import br.com.thomas.foursales.domain.entity.UserEntity;

public interface UserService {

    UserEntity findByUsername(String username);
}
