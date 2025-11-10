package br.com.thomas.foursales.infrastructure.service;

import br.com.thomas.foursales.domain.request.OrderRequest;
import br.com.thomas.foursales.domain.response.OrderResponse;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {

    OrderResponse create(String username, List<OrderRequest> orderList);

    List<OrderResponse> findOrdersByUser(String username);

    OrderResponse payment(Long id);

    BigDecimal findRevenueByDate(Integer month, Integer year, String orderStatus);
}
