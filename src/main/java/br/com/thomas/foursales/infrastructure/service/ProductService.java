package br.com.thomas.foursales.infrastructure.service;

import br.com.thomas.foursales.domain.request.ProductRequest;
import br.com.thomas.foursales.domain.response.ProductResponse;

public interface ProductService {

    ProductResponse create(ProductRequest request);
}
