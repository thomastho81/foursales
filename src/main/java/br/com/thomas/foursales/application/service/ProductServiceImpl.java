package br.com.thomas.foursales.application.service;

import br.com.thomas.foursales.domain.entity.ProductEntity;
import br.com.thomas.foursales.domain.repository.ProductRepository;
import br.com.thomas.foursales.domain.request.ProductRequest;
import br.com.thomas.foursales.domain.response.ProductResponse;
import br.com.thomas.foursales.infrastructure.exception.ResourceNotFoundException;
import br.com.thomas.foursales.infrastructure.mapper.ProductConverter;
import br.com.thomas.foursales.infrastructure.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private static final String PRODUCT = "produto";

    private final ProductRepository productRepository;

    private final ProductConverter productConverter;

    @Override
    public ProductResponse create(ProductRequest request) {
        ProductEntity productEntity = productConverter.toEntity(request);
        productEntity.setCreatedAt(LocalDateTime.now());

        ProductEntity savedEntity = productRepository.save(productEntity);

        return productConverter.toResponse(savedEntity);
    }

    @Override
    public ProductResponse update(String id, ProductRequest request) {
        ProductEntity productEntity = this.findByProductId(id);

        productEntity = productConverter.merge(request, productEntity);
        productEntity.setUpdatedAt(LocalDateTime.now());

        ProductEntity savedEntity = productRepository.save(productEntity);

        return productConverter.toResponse(savedEntity);
    }

    @Override
    public void delete(String id) {
        productRepository.delete(this.findByProductId(id));
    }

    @Override
    public ProductResponse findById(String id) {
        ProductEntity productEntity = this.findByProductId(id);

        return ProductResponse.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .description(productEntity.getDescription())
                .price(productEntity.getPrice())
                .category(productEntity.getCategory())
                .qtStock(productEntity.getQtStock())
                .createdAt(productEntity.getCreatedAt())
                .updatedAt(productEntity.getUpdatedAt())
                .build();
    }

    private ProductEntity findByProductId(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PRODUCT, id));
    }

    @Override
    public List<ProductEntity> findAllById(List<String> ids) {
        return productRepository.findAllById(ids);
    }

    @Override
    public List<ProductEntity> saveAll(List<ProductEntity> products) {
        return productRepository.saveAll(products);
    }
}
