package br.com.thomas.foursales.domain.request;

import javax.validation.constraints.NotNull;

public record OrderRequest(@NotNull String productId, @NotNull Integer qtProduct) {}
