package br.com.thomas.foursales.application.service;

import br.com.thomas.foursales.application.payment.PaymentGatewayService;
import br.com.thomas.foursales.domain.entity.OrderEntity;
import br.com.thomas.foursales.domain.entity.OrderItemEntity;
import br.com.thomas.foursales.domain.entity.ProductEntity;
import br.com.thomas.foursales.domain.entity.UserEntity;
import br.com.thomas.foursales.domain.enums.OrderStatusEnum;
import br.com.thomas.foursales.domain.enums.UserRoleEnum;
import br.com.thomas.foursales.domain.repository.OrderRepository;
import br.com.thomas.foursales.domain.request.OrderRequest;
import br.com.thomas.foursales.domain.response.OrderResponse;
import br.com.thomas.foursales.infrastructure.exception.OutOfStockException;
import br.com.thomas.foursales.infrastructure.exception.ResourceNotFoundException;
import br.com.thomas.foursales.infrastructure.mapper.OrderConverter;
import br.com.thomas.foursales.infrastructure.service.ProductService;
import br.com.thomas.foursales.infrastructure.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserService userService;

    @Mock
    private ProductService productService;

    @Mock
    private OrderConverter orderConverter;

    @Mock
    private PaymentGatewayService paymentGatewayService;

    @InjectMocks
    private OrderServiceImpl orderService;

    private UserEntity mockUser;
    private ProductEntity mockProduct;
    private OrderRequest orderRequest;
    private OrderEntity mockOrder;
    private OrderResponse mockOrderResponse;

    @BeforeEach
    void setUp() {
        mockUser = UserEntity.builder()
                .id(1L)
                .username("user1")
                .email("user1@test.com")
                .role(UserRoleEnum.USER)
                .build();

        mockProduct = ProductEntity.builder()
                .id("product-id-1")
                .name("Test Product")
                .description("Test Description")
                .price(BigDecimal.valueOf(100.00))
                .category("test")
                .qtStock(10)
                .createdAt(LocalDateTime.now())
                .build();

        orderRequest = new OrderRequest("product-id-1", 2);

        OrderItemEntity mockItem = OrderItemEntity.builder()
                .id(1L)
                .product(mockProduct)
                .quantity(2)
                .subtotal(BigDecimal.valueOf(200.00))
                .build();

        mockOrder = OrderEntity.builder()
                .id(1L)
                .user(mockUser)
                .status(OrderStatusEnum.PENDENTE)
                .value(BigDecimal.valueOf(200.00))
                .items(List.of(mockItem))
                .createdAt(LocalDateTime.now())
                .build();

        mockOrderResponse = OrderResponse.builder()
                .id(1L)
                .status(OrderStatusEnum.PENDENTE)
                .value(BigDecimal.valueOf(200.00))
                .build();
    }

    @Test
    void whenCreate_thenReturnsOrderResponse() {
        when(userService.findByUsername("user1")).thenReturn(mockUser);
        when(productService.findAllById(anyList())).thenReturn(List.of(mockProduct));
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(mockOrder);
        when(orderConverter.toResponse(any(OrderEntity.class))).thenReturn(mockOrderResponse);

        OrderResponse result = orderService.create("user1", List.of(orderRequest));

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(OrderStatusEnum.PENDENTE, result.getStatus());

        verify(userService, times(1)).findByUsername("user1");
        verify(productService, times(1)).findAllById(anyList());
        verify(orderRepository, times(1)).save(any(OrderEntity.class));
    }

    @Test
    void whenCreateWithInsufficientStock_thenThrowsOutOfStockException() {
        ProductEntity outOfStockProduct = ProductEntity.builder()
                .id("product-id-1")
                .name("Test Product")
                .qtStock(1)
                .build();

        OrderRequest largeOrderRequest = new OrderRequest("product-id-1", 10);

        when(userService.findByUsername("user1")).thenReturn(mockUser);
        when(productService.findAllById(anyList())).thenReturn(List.of(outOfStockProduct));

        assertThrows(
                OutOfStockException.class,
                () -> orderService.create("user1", List.of(largeOrderRequest)));

        verify(userService, times(1)).findByUsername("user1");
        verify(productService, times(1)).findAllById(anyList());
        verify(orderRepository, never()).save(any());
    }

    @Test
    void whenFindOrdersByUser_thenReturnsListOfOrderResponse() {
        when(userService.findByUsername("user1")).thenReturn(mockUser);
        when(orderRepository.findByUser(mockUser)).thenReturn(List.of(mockOrder));
        when(orderConverter.toResponse(any(OrderEntity.class))).thenReturn(mockOrderResponse);

        List<OrderResponse> result = orderService.findOrdersByUser("user1");

        assertNotNull(result);
        assertEquals(1, result.size());

        verify(userService, times(1)).findByUsername("user1");
        verify(orderRepository, times(1)).findByUser(mockUser);
    }

    @Test
    void whenPaymentWithInvalidOrderId_thenThrowsResourceNotFoundException() {
        Long invalidOrderId = 999L;
        when(orderRepository.findById(invalidOrderId)).thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> orderService.payment(invalidOrderId));

        verify(orderRepository, times(1)).findById(invalidOrderId);
        verify(paymentGatewayService, never()).mockPayment(any());
    }
}
