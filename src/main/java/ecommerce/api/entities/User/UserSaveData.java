package ecommerce.api.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UserSaveData(@NotNull @Email String username,
                           @NotNull String password, @NotNull Role role) {
}
