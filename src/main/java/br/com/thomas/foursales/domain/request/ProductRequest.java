package br.com.thomas.foursales.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    private String name;

    private String description;

    private BigDecimal price;

    private String category;

    private Long qtStock;

}
