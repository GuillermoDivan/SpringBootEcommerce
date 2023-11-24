package ecommerce.api.entities.Product;
import ecommerce.api.entities.Order.Order;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ProductUpdateData(@NotNull Long id, @NotNull String name,
                                @NotNull double price,
                                @NotNull String description, @NotNull List<Order> order) {
}
