package br.com.thomas.foursales.infrastructure.mapper;

import br.com.thomas.foursales.domain.entity.OrderEntity;
import br.com.thomas.foursales.domain.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderConverter {

    private final ModelMapper modelMapper;

    public OrderResponse toResponse(OrderEntity entity) {
        return modelMapper.map(entity, OrderResponse.class);
    }

}
