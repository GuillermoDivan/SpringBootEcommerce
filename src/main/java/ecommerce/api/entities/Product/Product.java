package ecommerce.api.entities.Product;
import ecommerce.api.entities.Order.Order;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name = "productos")
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double price;
    private String description;
    private boolean active;
    @ManyToMany
    private List<Order> order;

    public Product(ProductSaveData productSaveData){
        this.name = productSaveData.name();
        this.price = productSaveData.price();
        this.description = productSaveData.description();
        this.active = true;
    }
}
