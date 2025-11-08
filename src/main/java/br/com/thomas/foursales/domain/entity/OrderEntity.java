package br.com.thomas.foursales.domain.entity;

import br.com.thomas.foursales.domain.enums.OrderStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@Table(name = "TB_PEDIDO")
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ID_ITEM")
    @OneToMany(mappedBy = "order")
    private List<OrderItemEntity> items = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private OrderStatusEnum status;

    @Column(name = "VALOR_TOTAL")
    private BigDecimal value;

    @Column(name = "DT_CRIACAO")
    private LocalDateTime createdAt;
}
