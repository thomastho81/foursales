package br.com.thomas.foursales.domain.dto;

import java.math.BigDecimal;

public record UserPurchaseDTO(Long id, String username, BigDecimal value) {
}
