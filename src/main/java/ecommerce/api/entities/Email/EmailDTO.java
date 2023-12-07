package ecommerce.api.entities.Email;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailDTO(@NotBlank @Email String to,
    @NotBlank String subject,
    @NotBlank String message) {}
