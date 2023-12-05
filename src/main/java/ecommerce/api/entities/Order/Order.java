package ecommerce.api.entities.Order;
import ecommerce.api.entities.Client.Client;
import ecommerce.api.entities.Product.Product;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @ManyToMany
    private List<Product> productList;
    private boolean active;

    public Order(OrderSaveData orderSaveData){
        this.date = LocalDate.now();
        this.active = true;
        this.client = new Client();
        this.client.setId(orderSaveData.clientId());
    }
}
