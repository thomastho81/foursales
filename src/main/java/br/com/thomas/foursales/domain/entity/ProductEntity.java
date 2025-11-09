package br.com.thomas.foursales.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
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
@Table(name = "tb_produto")
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    @Id
    @Column(name = "id", columnDefinition = "CHAR(36)")
    private String id;

    @Column(name = "nome")
    private String name;

    @Column(name = "descricao")
    private String description;

    @Column(name = "preco", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "categoria")
    private String category;

    @Column(name = "qt_estoque")
    private Integer qtStock;

    @Column(name = "dt_criacao")
    private LocalDateTime createdAt;

    @Column(name = "dt_atualizacao")
    private LocalDateTime updatedAt;

    @PrePersist
    public void generateId() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
    }

    public boolean hasStockFor(int orderQuantity) {
        return this.qtStock >= orderQuantity;
    }

}
