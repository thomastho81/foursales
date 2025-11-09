package br.com.thomas.foursales.domain.repository;

import br.com.thomas.foursales.domain.entity.OrderEntity;
import br.com.thomas.foursales.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @EntityGraph(attributePaths = {"items", "items.product"})
    List<OrderEntity> findByUser(UserEntity user);
}
