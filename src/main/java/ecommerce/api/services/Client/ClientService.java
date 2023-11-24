package ecommerce.api.services.Client;
import ecommerce.api.entities.Client.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientService {
    ClientShowData saveClient(ClientSaveData clientSaveData);
    ClientShowData findClientById(Boolean active, Long id) throws EntityNotFoundException;
    ClientShowData findClientByName(Boolean active, String name) throws EntityNotFoundException;
    ClientShowData findClientByEmail(Boolean active, String email) throws EntityNotFoundException;
    Page<ClientShowData> findAllClientsByActive(Boolean active, Pageable paging);
    ClientShowData updateClient(ClientUpdateData clientUpdateData) throws EntityNotFoundException;
    boolean hideClient(Long id) throws EntityNotFoundException;
    boolean reactiveClient(Long id) throws EntityNotFoundException;
    boolean toggleClient(Long id) throws EntityNotFoundException;
    boolean deleteClient(Long id) throws EntityNotFoundException;
}
