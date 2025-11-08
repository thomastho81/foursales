package br.com.thomas.foursales.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@Entity
@Table(name = "TB_ITEM_PEDIDO")
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_PEDIDO", nullable = false)
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "ID_PRODUTO", nullable = false)
    private ProductEntity product;

    @Column(name = "QUANTIDADE")
    private Integer quantity;

    @Column(name = "SUBTOTAL", precision = 10, scale = 2)
    private BigDecimal subtotal;

}
