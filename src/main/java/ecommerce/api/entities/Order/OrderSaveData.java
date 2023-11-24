package ecommerce.api.entities.Order;
import ecommerce.api.entities.Client.Client;
import ecommerce.api.entities.Product.Product;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public record OrderSaveData(@NotNull LocalDateTime date,
                            @NotNull Client client,
                            @NotNull List<Product> productList) {
}


