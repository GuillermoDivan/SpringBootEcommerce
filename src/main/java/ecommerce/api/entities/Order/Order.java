package ecommerce.api.entities.Order;
import ecommerce.api.entities.Client.Client;
import ecommerce.api.entities.Product.Product;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
@NoArgsConstructor
@RequiredArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @ManyToMany
    private List<Product> productList;
    private boolean active = true;

    public Order(OrderSaveData orderSaveData){
        this.date = orderSaveData.date();
        this.client = orderSaveData.client();
        this.productList = orderSaveData.productList();
    }
}
