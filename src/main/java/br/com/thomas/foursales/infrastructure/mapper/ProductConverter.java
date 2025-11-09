package br.com.thomas.foursales.infrastructure.mapper;

import br.com.thomas.foursales.domain.entity.ProductEntity;
import br.com.thomas.foursales.domain.request.ProductRequest;
import br.com.thomas.foursales.domain.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductConverter {

    private final ModelMapper modelMapper;

    public ProductEntity toEntity(ProductRequest request) {
        return modelMapper.map(request, ProductEntity.class);
    }

    public ProductResponse toResponse(ProductEntity entity) {
        return modelMapper.map(entity, ProductResponse.class);
    }

    public ProductEntity merge(ProductRequest request, ProductEntity entity) {
        modelMapper.map(request, entity);
        return entity;
    }
}
