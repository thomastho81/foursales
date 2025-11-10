package br.com.thomas.foursales.application.service;

import br.com.thomas.foursales.domain.dto.UserPurchaseDTO;
import br.com.thomas.foursales.domain.dto.UserTicketAverageDTO;
import br.com.thomas.foursales.domain.entity.UserEntity;
import br.com.thomas.foursales.domain.enums.UserRoleEnum;
import br.com.thomas.foursales.domain.repository.UserRepository;
import br.com.thomas.foursales.domain.response.UserPurchaseResponse;
import br.com.thomas.foursales.domain.response.UserTicketAverageResponse;
import br.com.thomas.foursales.infrastructure.exception.ResourceNotFoundException;
import br.com.thomas.foursales.infrastructure.mapper.UserConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserConverter userConverter;

    @InjectMocks
    private UserServiceImpl userService;

    private UserEntity mockUser;
    private Page<UserPurchaseDTO> mockPurchaseDTOPage;
    private Page<UserPurchaseResponse> mockPurchaseResponsePage;
    private List<UserTicketAverageDTO> mockTicketAverageDTOs;
    private List<UserTicketAverageResponse> mockTicketAverageResponses;

    @BeforeEach
    void setUp() {
        mockUser = UserEntity.builder()
                .id(1L)
                .username("user1")
                .email("user1@test.com")
                .role(UserRoleEnum.USER)
                .build();

        UserPurchaseDTO purchaseDTO = new UserPurchaseDTO(1L, "user1", BigDecimal.valueOf(500.00));
        mockPurchaseDTOPage = new PageImpl<>(List.of(purchaseDTO));

        UserPurchaseResponse purchaseResponse = UserPurchaseResponse.builder()
                .id(1L)
                .username("user1")
                .value(BigDecimal.valueOf(500.00))
                .build();
        mockPurchaseResponsePage = new PageImpl<>(List.of(purchaseResponse));

        UserTicketAverageDTO ticketDTO = new UserTicketAverageDTO(1L, "user1", 100.00);
        mockTicketAverageDTOs = List.of(ticketDTO);

        UserTicketAverageResponse ticketResponse = UserTicketAverageResponse.builder()
                .id(1L)
                .username("user1")
                .ticketAverage(100.00)
                .build();
        mockTicketAverageResponses = List.of(ticketResponse);
    }

    @Test
    void whenFindUserPurchases_thenReturnsPageOfUserPurchaseResponse() {
        Pageable pageable = PageRequest.of(0, 5);
        when(userRepository.findUserPurchases(pageable)).thenReturn(mockPurchaseDTOPage);
        when(userConverter.toPurchaseResponse(any())).thenReturn(mockPurchaseResponsePage);

        Page<UserPurchaseResponse> result = userService.findUserPurchases(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("user1", result.getContent().get(0).getUsername());

        verify(userRepository, times(1)).findUserPurchases(pageable);
        verify(userConverter, times(1)).toPurchaseResponse(mockPurchaseDTOPage);
    }

    @Test
    void whenTicketAverage_thenReturnsListOfUserTicketAverageResponse() {
        when(userRepository.findUserTicketAverage()).thenReturn(mockTicketAverageDTOs);
        when(userConverter.toTicketAverageResponse(mockTicketAverageDTOs)).thenReturn(mockTicketAverageResponses);

        List<UserTicketAverageResponse> result = userService.tickerAverage();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("user1", result.get(0).getUsername());
        assertEquals(100.00, result.get(0).getTicketAverage());

        verify(userRepository, times(1)).findUserTicketAverage();
        verify(userConverter, times(1)).toTicketAverageResponse(mockTicketAverageDTOs);
    }

    @Test
    void whenFindByUsername_thenReturnsUserEntity() {
        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(mockUser));

        UserEntity result = userService.findByUsername("user1");

        assertNotNull(result);
        assertEquals("user1", result.getUsername());
        assertEquals("user1@test.com", result.getEmail());

        verify(userRepository, times(1)).findByUsername("user1");
    }

    @Test
    void whenFindByUsernameWithInvalidUsername_thenThrowsResourceNotFoundException() {
        String invalidUsername = "nonexistent";
        when(userRepository.findByUsername(invalidUsername)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> userService.findByUsername(invalidUsername));

        assertTrue(exception.getMessage().contains("usuário"));
        verify(userRepository, times(1)).findByUsername(invalidUsername);
    }
}
