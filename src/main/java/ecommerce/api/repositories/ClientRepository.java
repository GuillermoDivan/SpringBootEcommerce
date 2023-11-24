package ecommerce.api.repositories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ecommerce.api.entities.Client.Client;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{
    Optional<Client> findByNameAndActive(String name, Boolean active);
    Optional<Client> findByEmailAndActive(String email,Boolean active);
    Page<Client> findAllClientsByActive(Boolean active, Pageable paging);
}
