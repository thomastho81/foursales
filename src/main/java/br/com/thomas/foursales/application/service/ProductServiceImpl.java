package br.com.thomas.foursales.application.service;

import br.com.thomas.foursales.domain.entity.ProductEntity;
import br.com.thomas.foursales.domain.repository.ProductRepository;
import br.com.thomas.foursales.domain.request.ProductRequest;
import br.com.thomas.foursales.domain.response.ProductResponse;
import br.com.thomas.foursales.infrastructure.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductResponse create(ProductRequest request) {

        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(request.getName());

        ProductEntity savedEntity = productRepository.save(ProductEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .category(request.getCategory())
                .qtStock(request.getQtStock())
                .createdAt(LocalDateTime.now())
                .build());

        return ProductResponse.builder()
                .id(savedEntity.getId())
                .name(savedEntity.getName())
                .description(savedEntity.getDescription())
                .price(savedEntity.getPrice())
                .category(savedEntity.getCategory())
                .qtStock(savedEntity.getQtStock())
                .createdAt(savedEntity.getCreatedAt())
                .updatedAt(savedEntity.getUpdatedAt())
                .build();
    }
}
