package ecommerce.api.entities.Client;
import ecommerce.api.entities.Order.Order;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String address;
    private Integer phone;
    @OneToMany(mappedBy = "client")
    private List<Order> orders;
    private boolean active = true;


    public Client(ClientSaveData clientSaveData) {
        this.name = clientSaveData.name();
        this.email = clientSaveData.email();
        this.password = clientSaveData.password();
        this.address = clientSaveData.address();
        this.phone = clientSaveData.phone();
    }
}
