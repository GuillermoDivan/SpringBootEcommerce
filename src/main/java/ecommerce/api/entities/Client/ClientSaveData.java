package ecommerce.api.entities.Client;
import ecommerce.api.entities.Order.Order;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ClientSaveData(@NotNull String name,
                             @NotNull @Email String email,
                             @NotNull String password,
                             @NotNull String address,
                             @NotNull Integer phone,
                             List<Order> orders) {
}
