package ecommerce.api.repositories;
import ecommerce.api.entities.Client.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ecommerce.api.entities.Order.Order;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
    Optional<Order> findByClientNameAndActive(String clientName, Boolean active);
    Page<Order> findAllOrdersByActive(Boolean active, Pageable paging);
}
