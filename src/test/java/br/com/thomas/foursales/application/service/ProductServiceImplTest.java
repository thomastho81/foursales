package br.com.thomas.foursales.application.service;

import br.com.thomas.foursales.domain.entity.ProductEntity;
import br.com.thomas.foursales.domain.repository.ProductRepository;
import br.com.thomas.foursales.domain.request.ProductRequest;
import br.com.thomas.foursales.domain.response.ProductResponse;
import br.com.thomas.foursales.infrastructure.exception.ResourceNotFoundException;
import br.com.thomas.foursales.infrastructure.mapper.ProductConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductConverter productConverter;

    @InjectMocks
    private ProductServiceImpl productService;

    private ProductRequest productRequest;
    private ProductEntity productEntity;
    private ProductResponse productResponse;
    private static final String PRODUCT_ID = "test-product-id";

    @BeforeEach
    void setUp() {
        productRequest = ProductRequest.builder()
                .name("Test Product")
                .description("Test Description")
                .price(BigDecimal.valueOf(100.00))
                .category("test")
                .qtStock(10)
                .build();

        productEntity = ProductEntity.builder()
                .id(PRODUCT_ID)
                .name("Test Product")
                .description("Test Description")
                .price(BigDecimal.valueOf(100.00))
                .category("test")
                .qtStock(10)
                .createdAt(LocalDateTime.now())
                .build();

        productResponse = ProductResponse.builder()
                .id(PRODUCT_ID)
                .name("Test Product")
                .description("Test Description")
                .price(BigDecimal.valueOf(100.00))
                .category("test")
                .qtStock(10)
                .build();
    }

    @Test
    void whenCreate_thenReturnsProductResponse() {
        when(productConverter.toEntity(any(ProductRequest.class))).thenReturn(productEntity);
        when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity);
        when(productConverter.toResponse(any(ProductEntity.class))).thenReturn(productResponse);

        ProductResponse result = productService.create(productRequest);

        assertNotNull(result);
        assertEquals(PRODUCT_ID, result.getId());
        assertEquals("Test Product", result.getName());
        assertEquals(BigDecimal.valueOf(100.00), result.getPrice());

        verify(productConverter, times(1)).toEntity(productRequest);
        verify(productRepository, times(1)).save(any(ProductEntity.class));
        verify(productConverter, times(1)).toResponse(productEntity);
    }

    @Test
    void whenFindByIdWithInvalidId_thenThrowsResourceNotFoundException() {
        String invalidId = "invalid-id";
        when(productRepository.findById(invalidId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> productService.findById(invalidId));

        assertTrue(exception.getMessage().contains("produto"));
        verify(productRepository, times(1)).findById(invalidId);
    }

    @Test
    void whenUpdate_thenReturnsUpdatedProductResponse() {
        when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(productEntity));
        when(productConverter.merge(any(ProductRequest.class), any(ProductEntity.class))).thenReturn(productEntity);
        when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity);
        when(productConverter.toResponse(any(ProductEntity.class))).thenReturn(productResponse);

        ProductResponse result = productService.update(PRODUCT_ID, productRequest);

        assertNotNull(result);
        assertEquals(PRODUCT_ID, result.getId());

        verify(productRepository, times(1)).findById(PRODUCT_ID);
        verify(productConverter, times(1)).merge(productRequest, productEntity);
        verify(productRepository, times(1)).save(productEntity);
    }

    @Test
    void whenDeleteWithInvalidId_thenThrowsResourceNotFoundException() {
        String invalidId = "invalid-id";
        when(productRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> productService.delete(invalidId));

        verify(productRepository, times(1)).findById(invalidId);
        verify(productRepository, never()).delete(any());
    }
}
