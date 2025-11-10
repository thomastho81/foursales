package br.com.thomas.foursales.infrastructure.service;

import br.com.thomas.foursales.domain.entity.UserEntity;
import br.com.thomas.foursales.domain.response.UserPurchaseResponse;
import br.com.thomas.foursales.domain.response.UserTicketAverageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    Page<UserPurchaseResponse> findUserPurchases(Pageable pageable);
    UserEntity findByUsername(String username);
    List<UserTicketAverageResponse> tickerAverage();
}
