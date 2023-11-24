package ecommerce.api.repositories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ecommerce.api.entities.Product.Product;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByNameAndActive(String name, Boolean active);
    Page<Product> findAllProductByActive(Boolean active, Pageable paging);
}
