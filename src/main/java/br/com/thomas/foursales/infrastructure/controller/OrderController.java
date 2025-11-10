package br.com.thomas.foursales.infrastructure.controller;

import br.com.thomas.foursales.domain.request.OrderRequest;
import br.com.thomas.foursales.domain.response.OrderResponse;
import br.com.thomas.foursales.infrastructure.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/user/{username}")
    public ResponseEntity<OrderResponse> create(@PathVariable String username,
                                                @RequestBody @Valid List<OrderRequest> request) {
        log.info("metódo=create - requisição para criar pedido para usuário {} com {} pedidos", username, request.size());
        OrderResponse orderResponse = orderService.create(username, request);
        log.info("metódo=create - pedido do usuário {} criado com sucesso", username);
        return ResponseEntity
                .status(HttpStatusCode.valueOf(HttpStatus.CREATED.value()))
                .body(orderResponse);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<OrderResponse>> findOrdersByUser(@PathVariable String username) {
        log.info("metódo=findOrdersByUser - requisição para encontrar pedidos do usuário {}", username);

        List<OrderResponse> ordersByUser = orderService.findOrdersByUser(username);

        log.info("metódo=findOrdersByUser - {} pedidos encontrados para o usuário {}", username, ordersByUser.size());

        return ResponseEntity.ok(ordersByUser);
    }

    @PutMapping("/order/{order-id}/payment")
    public ResponseEntity<OrderResponse> orderPayment(@PathVariable("order-id") Long id) {
        log.info("metódo=orderPayment - requisição para pagar pedido de id {}", id);

        OrderResponse orderResponse = orderService.payment(id);

        log.info("metódo=orderPayment - pedido {} pago com sucesso", id);

        return ResponseEntity.ok(orderResponse);
    }

    @GetMapping("/monthly-revenue")
    public ResponseEntity<BigDecimal> findRevenueByDate(@RequestParam(required = false) Integer month,
                                                        @RequestParam(required = false) Integer year,
                                                        @RequestParam(required = false, defaultValue = "PAGO") String orderStatus) {
        log.info("metódo=findRevenueByDate - requisição para encontrar receita mensal do mes {} de {} para o status {}", month, year, orderStatus);

        BigDecimal revenueByDate = orderService.findRevenueByDate(month, year, orderStatus);

        log.info("metódo=findRevenueByDate - receita de {} para o mes {} de {} e status {}", revenueByDate, month, year, orderStatus);

        return ResponseEntity.ok(revenueByDate);
    }

}
