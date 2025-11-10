package br.com.thomas.foursales.application.service;

import br.com.thomas.foursales.domain.dto.UserTicketAverageDTO;
import br.com.thomas.foursales.domain.entity.UserEntity;
import br.com.thomas.foursales.domain.repository.UserRepository;
import br.com.thomas.foursales.domain.response.UserPurchaseResponse;
import br.com.thomas.foursales.domain.response.UserTicketAverageResponse;
import br.com.thomas.foursales.infrastructure.exception.ResourceNotFoundException;
import br.com.thomas.foursales.infrastructure.mapper.UserConverter;
import br.com.thomas.foursales.infrastructure.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String USER = "usuário";

    private final UserRepository userRepository;

    private final UserConverter userConverter;

    @Override
    public Page<UserPurchaseResponse> findUserPurchases(Pageable pageable) {
        var dto = userRepository.findUserPurchases(pageable);
        return userConverter.toPurchaseResponse(dto);
    }

    @Override
    public List<UserTicketAverageResponse> tickerAverage() {
        List<UserTicketAverageDTO> userTicketAverage = userRepository.findUserTicketAverage();
        return userConverter.toTicketAverageResponse(userTicketAverage);
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(USER));
    }
}
