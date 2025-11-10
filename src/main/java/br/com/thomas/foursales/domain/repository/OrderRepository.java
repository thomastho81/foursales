package br.com.thomas.foursales.domain.repository;

import br.com.thomas.foursales.domain.entity.OrderEntity;
import br.com.thomas.foursales.domain.entity.UserEntity;
import br.com.thomas.foursales.domain.enums.OrderStatusEnum;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @EntityGraph(attributePaths = {"items", "items.product"})
    List<OrderEntity> findByUser(UserEntity user);

    @Query(" SELECT SUM(o.value) " +
            " FROM OrderEntity o " +
            " WHERE YEAR(o.createdAt) = :year " +
            " AND MONTH(o.createdAt) = :month " +
            " AND o.status = :status")
    BigDecimal findRevenueByDate(@Param("year") int year,
                                 @Param("month") int month,
                                 @Param("status") OrderStatusEnum status);
}
