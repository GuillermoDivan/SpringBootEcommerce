package ecommerce.api.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UserShowData(Long id, String username) {

    public UserShowData(User user){
        this(user.getId(), user.getUsername());
    }
}
