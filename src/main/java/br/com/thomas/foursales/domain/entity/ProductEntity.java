package br.com.thomas.foursales.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@Table(name = "TB_PRODUTO")
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "NOME")
    private String name;

    @Column(name = "DESCRICAO")
    private String description;

    @Column(name = "PRECO", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "CATEGORIA")
    private String category;

    @Column(name = "QT_ESTOQUE")
    private Long qtStock;

    @Column(name = "DT_CRIACAO")
    private LocalDateTime createdAt;

    @Column(name = "DT_ATUALIZACAO")
    private LocalDateTime updatedAt;
}
