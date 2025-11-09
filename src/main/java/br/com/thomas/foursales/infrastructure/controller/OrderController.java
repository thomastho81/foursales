package br.com.thomas.foursales.infrastructure.controller;

import br.com.thomas.foursales.domain.request.OrderRequest;
import br.com.thomas.foursales.domain.response.OrderResponse;
import br.com.thomas.foursales.infrastructure.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/user/{username}")
    public ResponseEntity<OrderResponse> create(@PathVariable String username,
                                                @RequestBody @Valid List<OrderRequest> request) {

        OrderResponse orderResponse = orderService.create(username, request);

        return ResponseEntity
                .status(HttpStatusCode.valueOf(HttpStatus.CREATED.value()))
                .body(orderResponse);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<OrderResponse>> findOrdersByUser(@PathVariable String username) {
        List<OrderResponse> ordersByUser = orderService.findOrdersByUser(username);

        return ResponseEntity.ok(ordersByUser);
    }

    @PutMapping("/order/{order-id}/payment")
    public ResponseEntity<OrderResponse> orderPayment(@PathVariable("order-id") Long id) {
        OrderResponse orderResponse = orderService.payment(id);

        return ResponseEntity.ok(orderResponse);
    }

}
