package br.com.thomas.foursales.application.service;

import br.com.thomas.foursales.domain.entity.ProductEntity;
import br.com.thomas.foursales.domain.repository.ProductRepository;
import br.com.thomas.foursales.domain.request.ProductRequest;
import br.com.thomas.foursales.domain.response.ProductResponse;
import br.com.thomas.foursales.infrastructure.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductResponse create(ProductRequest request) {

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

    @Override
    public ProductResponse update(UUID id, ProductRequest request) {
        //TODO: exception
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("nao encontrei"));

        productEntity.setName(request.getName());
        productEntity.setDescription(request.getDescription());
        productEntity.setPrice(request.getPrice());
        productEntity.setCategory(request.getCategory());
        productEntity.setQtStock(request.getQtStock());
        productEntity.setUpdatedAt(LocalDateTime.now());

        ProductEntity savedEntity = productRepository.save(productEntity);

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
