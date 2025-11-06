package br.com.thomas.foursales.infrastructure.controller;

import br.com.thomas.foursales.domain.request.ProductRequest;
import br.com.thomas.foursales.domain.response.ProductResponse;
import br.com.thomas.foursales.infrastructure.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    //TODO: logs, responseStatus decente; exception handler
    @PostMapping("/")
    public ResponseEntity<ProductResponse> create(@RequestBody ProductRequest request) {

        ProductResponse productResponse = productService.create(request);

        return ResponseEntity.ok(productResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable UUID id, @RequestBody ProductRequest request) {
        ProductResponse productResponse = productService.update(id, request);

        return ResponseEntity.ok(productResponse);
    }

}
