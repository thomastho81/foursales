package br.com.thomas.foursales.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
//TODO: transformar em record
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
