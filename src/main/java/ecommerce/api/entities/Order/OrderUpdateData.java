package ecommerce.api.entities.Order;
import ecommerce.api.entities.Product.Product;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record OrderUpdateData(@NotNull Long id,
                              @NotNull List<Product> productList) {
}
