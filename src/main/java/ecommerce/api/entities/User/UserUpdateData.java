package ecommerce.api.entities.User;
import jakarta.validation.constraints.NotNull;

public record UserUpdateData(@NotNull Long id,
                             @NotNull String password) {
}
