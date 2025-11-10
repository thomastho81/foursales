package br.com.thomas.foursales.infrastructure.mapper;

import br.com.thomas.foursales.domain.dto.UserPurchaseDTO;
import br.com.thomas.foursales.domain.dto.UserTicketAverageDTO;
import br.com.thomas.foursales.domain.response.UserPurchaseResponse;
import br.com.thomas.foursales.domain.response.UserTicketAverageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserConverter {

    public Page<UserPurchaseResponse> toPurchaseResponse(Page<UserPurchaseDTO> dtoPage) {
        List<UserPurchaseResponse> purchaseResponses = dtoPage.getContent().stream()
                .map(dto -> UserPurchaseResponse.builder()
                        .id(dto.id())
                        .username(dto.username())
                        .value(dto.value())
                        .build())
                .toList();

        return new PageImpl<>(purchaseResponses, dtoPage.getPageable(), dtoPage.getTotalElements()
        );
    }

    public List<UserTicketAverageResponse> toTicketAverageResponse(List<UserTicketAverageDTO> dtoList) {
        return dtoList.stream()
                .map(dto -> UserTicketAverageResponse.builder()
                        .id(dto.id())
                        .username(dto.username())
                        .ticketAverage(dto.ticketAverage())
                        .build())
                .toList();
    }
}
