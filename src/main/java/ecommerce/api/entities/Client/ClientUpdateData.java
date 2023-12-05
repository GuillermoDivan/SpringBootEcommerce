package ecommerce.api.entities.Client;
import ecommerce.api.entities.Order.Order;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ClientUpdateData(@NotNull Long id,
                               String name,
                                String password,
                               String address,
                               Integer phone,
                               @NotNull List<Order> orders) {
}
