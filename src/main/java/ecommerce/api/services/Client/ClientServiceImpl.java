package ecommerce.api.services.Client;
import ecommerce.api.entities.Client.Client;
import ecommerce.api.entities.Client.ClientSaveData;
import ecommerce.api.entities.Client.ClientShowData;
import ecommerce.api.entities.Client.ClientUpdateData;
import ecommerce.api.repositories.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService{

    private final ClientRepository clientRepository;

    @Override
    public ClientShowData saveClient(ClientSaveData clientSaveData) {
        Client client = new Client(clientSaveData);
        this.clientRepository.save(client);
        return new ClientShowData(client);
    }

    @Override
    public ClientShowData findClientById(Boolean active, Long id) throws EntityNotFoundException {
        var clientShowData = this.clientRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return new ClientShowData(clientShowData);
    }

    @Override
    public ClientShowData findClientByName(Boolean active, String name) throws EntityNotFoundException {
        var clientShowData = this.clientRepository.findByNameAndActive(name, active).orElseThrow(EntityNotFoundException::new);
        return new ClientShowData(clientShowData);
    }

    @Override
    public ClientShowData findClientByEmail(Boolean active, String email)  throws EntityNotFoundException {
        var clientShowData = this.clientRepository.findByEmailAndActive(email, active).orElseThrow(EntityNotFoundException::new);
        return new ClientShowData(clientShowData);
    }

    @Override
    public Page<ClientShowData> findAllClientsByActive(Boolean active, Pageable paging) {
        return this.clientRepository.findAllClientsByActive(active, paging).map(ClientShowData::new);
    }

    @Override
    public ClientShowData updateClient(ClientUpdateData clientUpdateData) throws EntityNotFoundException {
        var client = this.clientRepository.findById(clientUpdateData.id()).orElseThrow(EntityNotFoundException::new);
        if (client.isActive()) {
            if (clientUpdateData.name() != null && !clientUpdateData.name().isEmpty()) {
                client.setName(clientUpdateData.name());
            }
            if (clientUpdateData.password() != null && !clientUpdateData.password().isEmpty()) {
                client.setPassword(clientUpdateData.password());
            }
            if (clientUpdateData.address() != null && !clientUpdateData.address().isEmpty()) {
                client.setAddress(clientUpdateData.address());
            }
            if (clientUpdateData.phone() != null) {
                client.setPhone(clientUpdateData.phone());
            }
            this.clientRepository.save(client);
            return new ClientShowData(client);
        } else {
            return null;
        }
    }


    @Override
    public boolean hideClient(Long id) {
        var client = this.clientRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        if (client.isActive()) {
                client.setActive(false);
                this.clientRepository.save(client);
                return true;
            } else { return false; }
    }

    @Override
    public boolean reactiveClient(Long id) {
        var client = this.clientRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        if (!client.isActive()) {
            client.setActive(true);
            this.clientRepository.save(client);
            return true;
        } else { return false; }
    }

    @Override
    public boolean toggleClient(Long id) {
        var client = this.clientRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        if (client != null) {
            client.setActive(!client.isActive());
            this.clientRepository.save(client);
            return true;
        } else { return false; }
    }

    @Override
    public boolean deleteClient(Long id) {
        var client = this.clientRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        if (client != null) {
            this.clientRepository.delete(client);
            return true;
        } else { return false; }
    }
}
