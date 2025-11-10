package br.com.thomas.foursales.domain.repository;

import br.com.thomas.foursales.domain.dto.UserPurchaseDTO;
import br.com.thomas.foursales.domain.dto.UserTicketAverageDTO;
import br.com.thomas.foursales.domain.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    @Query("SELECT new br.com.thomas.foursales.domain.dto.UserPurchaseDTO( " +
            " u.id, u.username, SUM(o.value) ) " +
            " FROM UserEntity u " +
            " JOIN OrderEntity o " +
            " ON o.user.id = u.id " +
            " WHERE o.status = 'PAGO' " +
            " GROUP BY u.id, u.username " +
            " ORDER BY SUM(o.value) ")
    Page<UserPurchaseDTO> findUserPurchases(Pageable pageable);

    @Query("SELECT new br.com.thomas.foursales.domain.dto.UserTicketAverageDTO( " +
            " u.id, u.username, AVG(o.value) ) " +
            " FROM UserEntity u " +
            " JOIN OrderEntity o " +
            " ON o.user.id = u.id " +
            " WHERE o.status = 'PAGO' " +
            " GROUP BY u.id, u.username ")
    List<UserTicketAverageDTO> findUserTicketAverage();
}
