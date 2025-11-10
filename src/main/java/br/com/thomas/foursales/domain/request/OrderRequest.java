package br.com.thomas.foursales.domain.request;


import jakarta.validation.constraints.NotNull;

public record OrderRequest(@NotNull String productId, @NotNull Integer qtProduct) {}
