package br.com.thomas.foursales.infrastructure.controller;

import br.com.thomas.foursales.domain.request.ProductRequest;
import br.com.thomas.foursales.domain.response.ProductResponse;
import br.com.thomas.foursales.infrastructure.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    //TODO: pq validation notnull n ta funcionando
    @PostMapping("/")
    public ResponseEntity<ProductResponse> create(@RequestBody @Valid ProductRequest request) {
        log.info("metódo=create - requisição para criar produto {} com valor {}", request.getName(), request.getPrice());

        ProductResponse productResponse = productService.create(request);

        log.info("metódo=create - produto {} de id {} cadastrado com sucesso", productResponse.getName(), productResponse.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(productResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable String id, @RequestBody ProductRequest request) {
        log.info("metódo=update - requisição para atualizar produto {} de id {}", request.getName(), id);

        ProductResponse productResponse = productService.update(id, request);

        log.info("metódo=create - produto de id {} atualizado com sucesso", id);

        return ResponseEntity.ok(productResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.info("metódo=delete - requisição para deletar produto de id {}", id);

        productService.delete(id);

        log.info("metódo=delete - produto {} deletado com sucesso", id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable String id) {
        log.info("metódo=findById - requisição para encontrar produto de id {}", id);

        ProductResponse product = productService.findById(id);

        log.info("metódo=findById -  produto de id {} encontrado com sucesso", id);

        return ResponseEntity.ok(product);
    }

}
