package ecommerce.api.entities.Order;
import jakarta.validation.constraints.NotNull;

public record OrderSaveData(@NotNull Long clientId) {
}


