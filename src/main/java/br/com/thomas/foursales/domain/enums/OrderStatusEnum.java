package br.com.thomas.foursales.domain.enums;

import br.com.thomas.foursales.infrastructure.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum OrderStatusEnum {

    PENDENTE(1, "PENDENTE"),
    PAGO(2, "PAGO"),
    CANCELADO(3, "CANCELADO");

    private final Integer id;

    private final String status;

    public static OrderStatusEnum getFromStatus(String status) {
        return Arrays.stream(values())
                .filter(order -> order.getStatus().equals(status))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("status", status));
    }
}
