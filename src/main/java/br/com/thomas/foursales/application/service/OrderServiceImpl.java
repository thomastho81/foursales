package br.com.thomas.foursales.application.service;

import br.com.thomas.foursales.application.payment.PaymentGatewayService;
import br.com.thomas.foursales.domain.entity.OrderEntity;
import br.com.thomas.foursales.domain.entity.OrderItemEntity;
import br.com.thomas.foursales.domain.entity.ProductEntity;
import br.com.thomas.foursales.domain.entity.UserEntity;
import br.com.thomas.foursales.domain.enums.OrderStatusEnum;
import br.com.thomas.foursales.domain.repository.OrderRepository;
import br.com.thomas.foursales.domain.request.OrderRequest;
import br.com.thomas.foursales.domain.request.PaymentRequest;
import br.com.thomas.foursales.domain.response.OrderResponse;
import br.com.thomas.foursales.infrastructure.exception.OrderUnavailableException;
import br.com.thomas.foursales.infrastructure.exception.OutOfStockException;
import br.com.thomas.foursales.infrastructure.exception.PaymentException;
import br.com.thomas.foursales.infrastructure.exception.ResourceNotFoundException;
import br.com.thomas.foursales.infrastructure.mapper.OrderConverter;
import br.com.thomas.foursales.infrastructure.service.OrderService;
import br.com.thomas.foursales.infrastructure.service.ProductService;
import br.com.thomas.foursales.infrastructure.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final UserService userService;

    private final ProductService productService;

    private final OrderConverter orderConverter;

    private final PaymentGatewayService paymentGatewayService;

    private static final String OUT_OF_STOCK = "Estoque insuficiente para o produto %s";
    private static final String ORDER_UNAVAILABLE = "Não foi possível realizar o pagamento pois o pedido está em %s";
    private static final String ORDER_CANCELED = "Pedido cancelado: produto %s sem estoque suficiente";
    private static final String PAYMENT_FAILED = "Pagamento falhou para o usuário %s";

    @Override
    @Transactional
    public OrderResponse create(String username, List<OrderRequest> orderList) {
        UserEntity userEntity = userService.findByUsername(username);

        List<String> productIds = orderList.stream()
                .map(OrderRequest::productId)
                .toList();
        Map<String, ProductEntity> productsMapped = productService.findAllById(productIds).stream()
                .collect(Collectors.toMap(ProductEntity::getId, product -> product));

        OrderEntity orderEntity = OrderEntity.builder()
                .status(OrderStatusEnum.PENDENTE)
                .createdAt(LocalDateTime.now())
                .user(userEntity)
                .build();

        List<OrderItemEntity> orderItemEntities = orderList.stream()
                .map(order -> {
                    ProductEntity product = productsMapped.get(order.productId());
                    if (Objects.isNull(product)) throw new ResourceNotFoundException("produto", order.productId());
                    if (!product.hasStockFor(order.qtProduct())) throw new OutOfStockException(String.format(OUT_OF_STOCK, product.getName()));

                    OrderItemEntity orderItemEntity = new OrderItemEntity();
                    orderItemEntity.setOrder(orderEntity);
                    orderItemEntity.setProduct(product);
                    orderItemEntity.setQuantity(order.qtProduct());
                    orderItemEntity.calculateSubtotal();

                    return orderItemEntity;
                })
                .toList();

        orderEntity.setItems(orderItemEntities);
        orderEntity.calculateTotalValue();

        var savedOrder = orderRepository.save(orderEntity);

        return orderConverter.toResponse(savedOrder);
    }


    @Override
    public List<OrderResponse> findOrdersByUser(String username) {
        UserEntity userEntity = userService.findByUsername(username);
        List<OrderEntity> orders = orderRepository.findByUser(userEntity);

        return orders.stream()
                .map(orderConverter::toResponse)
                .toList();
    }

    @Override
    public OrderResponse payment(Long id) {
        OrderEntity orderEntity = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("pedido"));
        if (!OrderStatusEnum.PENDENTE.equals(orderEntity.getStatus())) throw new OrderUnavailableException(String.format(ORDER_UNAVAILABLE, orderEntity.getStatus().getStatus()));

        orderEntity.getItems().forEach(item -> {
            ProductEntity product = item.getProduct();

            if (!product.hasStockFor(item.getQuantity())) {
                orderEntity.setStatus(OrderStatusEnum.CANCELADO);
                orderEntity.setUpdatedAt(LocalDateTime.now());

                throw new OutOfStockException(
                        String.format(ORDER_CANCELED, product.getName())
                );
            }
        });

        orderEntity.setStatus(OrderStatusEnum.PAGO);
        orderEntity.setUpdatedAt(LocalDateTime.now());

        List<ProductEntity> productsToUpdate = orderEntity.getItems().stream()
                .map(item -> {
                    ProductEntity product = item.getProduct();
                    product.setQtStock(product.getQtStock() - item.getQuantity());
                    product.setUpdatedAt(LocalDateTime.now());
                    return product;
                })
                .toList();
        productService.saveAll(productsToUpdate);

        boolean isPaymentSuccess = paymentGatewayService.mockPayment(new PaymentRequest(orderEntity.getUser().getEmail()));
        if (!isPaymentSuccess) throw new PaymentException(String.format(PAYMENT_FAILED, orderEntity.getUser().getId()));

        return orderConverter.toResponse(orderRepository.save(orderEntity));
    }

    @Override
    public BigDecimal findRevenueByDate(Integer month, Integer year, String orderStatus) {
        LocalDate now = LocalDate.now();
        month = Objects.isNull(month) ? now.getMonthValue() : month;
        year = Objects.isNull(year) ? now.getYear() : year;

        OrderStatusEnum orderStatusEnum = OrderStatusEnum.getFromStatus(orderStatus);

        return orderRepository.findRevenueByDate(year, month, orderStatusEnum);
    }
}
