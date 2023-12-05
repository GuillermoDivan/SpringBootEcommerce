package ecommerce.api.repositories;
import ecommerce.api.entities.Client.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ecommerce.api.entities.Order.Order;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
    Optional<Order> findByClientNameAndActive(String clientName, Boolean active);
    Page<Order> findAllOrdersByActive(Boolean active, Pageable paging);

    @Query("""
    SELECT O FROM Order O 
    WHERE O.date = :date1 and O.active = :active 
    """)
    Page<Order>findAllByDateAndActive(LocalDate date1, Boolean active, Pageable paging);

    @Query("""
    SELECT O FROM Order O 
    WHERE O.date BETWEEN :dateBefore AND :dateAfter 
    AND O.active = :active 
    """)
    Page<Order>findAllBetweenDatesAndActive(LocalDate dateBefore, LocalDate dateAfter, Boolean active, Pageable paging);


}
