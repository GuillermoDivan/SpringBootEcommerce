package ecommerce.api.entities.Email;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public record EmailFileDTO(@NotBlank @Email String to,
                           @NotBlank String subject,
                           @NotBlank String message,
                           @NotBlank MultipartFile file) {}