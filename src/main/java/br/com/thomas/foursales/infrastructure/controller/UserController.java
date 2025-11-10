package br.com.thomas.foursales.infrastructure.controller;

import br.com.thomas.foursales.domain.response.UserPurchaseResponse;
import br.com.thomas.foursales.domain.response.UserTicketAverageResponse;
import br.com.thomas.foursales.infrastructure.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/purchases")
    public ResponseEntity<Page<UserPurchaseResponse>> findUserPurchases(@PageableDefault(size = 5, direction = Sort.Direction.DESC) Pageable pageable) {
        log.info("metódo=findUserPurchases - requisição para encontrar o top {} usuários que mais compraram ", pageable.getPageSize());

        Page<UserPurchaseResponse> userPurchaseResponses = userService.findUserPurchases(pageable);

        log.info("metódo=findUserPurchases - encontrados {} usuários que mais compraram ", pageable.getPageSize());

        return ResponseEntity.ok(userPurchaseResponses);
    }

    @GetMapping("/ticket-average")
    public ResponseEntity<List<UserTicketAverageResponse>> findUsersTicketAverage() {
        log.info("metódo=findUsersTicketAverage - requisição para encontrar o ticket médio dos pedidos de cada usuário");

        List<UserTicketAverageResponse> ticketAverageResponses = userService.tickerAverage();

        log.info("metódo=findUsersTicketAverage - ticket médio dos pedidos de cada usuário encontrados com sucesso");

        return ResponseEntity.ok(ticketAverageResponses);
    }
}
