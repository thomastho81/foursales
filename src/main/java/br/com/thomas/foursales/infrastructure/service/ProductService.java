package br.com.thomas.foursales.infrastructure.service;

import br.com.thomas.foursales.domain.entity.ProductEntity;
import br.com.thomas.foursales.domain.request.ProductRequest;
import br.com.thomas.foursales.domain.response.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse create(ProductRequest request);

    ProductResponse update(String id, ProductRequest request);

    void delete(String id);

    ProductResponse findById(String id);

    List<ProductEntity> findAllById(List<String> ids);

    List<ProductEntity> saveAll(List<ProductEntity> products);
}
