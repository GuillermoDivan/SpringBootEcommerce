package ecommerce.api.entities.Order;
import ecommerce.api.entities.Client.Client;
import ecommerce.api.entities.Product.Product;
import java.time.LocalDateTime;
import java.util.List;

public record OrderShowData(LocalDateTime Date,
                            Client client, List<Product> productList) {

    public OrderShowData(Order order) {
        this(order.getDate(), order.getClient(),
                order.getProductList());
    }
}
