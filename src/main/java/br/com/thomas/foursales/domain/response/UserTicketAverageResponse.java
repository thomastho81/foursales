package br.com.thomas.foursales.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserTicketAverageResponse {

    private Long id;

    private String username;

    private Double ticketAverage;
}
