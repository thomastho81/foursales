package br.com.thomas.foursales.domain.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    @DecimalMin(value = "0.0", message = "Preço deve ser maior ou igual a 0")
    @Digits(integer = 9, fraction = 2, message = "Preço deve ter no máximo 9 digitos e duas casas decimais")
    private BigDecimal price;

    @NotNull
    @NotBlank
    private String category;

    @NotNull
    private Integer qtStock;

}
