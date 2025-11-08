package br.com.thomas.foursales.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatusEnum {

    PENDENTE(1, "PENDENTE");

    private final Integer id;

    private final String status;
}
