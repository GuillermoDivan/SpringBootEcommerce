package ecommerce.api.entities.Order;
import ecommerce.api.entities.Client.Client;
import ecommerce.api.entities.Product.Product;
import java.time.LocalDate;
import java.util.List;

public record OrderShowData(LocalDate Date,
                            Client client, List<Product> productList) {

    public OrderShowData(Order order) {
        this(order.getDate(), order.getClient(),
                order.getProductList());
    }
}
