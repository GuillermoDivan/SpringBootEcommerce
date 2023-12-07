package ecommerce.api.services.Authentication;
import ecommerce.api.entities.User.UserSaveData;
import ecommerce.api.security.JWTTokenData;

public interface AuthenticationService {

    JWTTokenData authenticate(UserSaveData userSaveData);
    boolean isSelforAdmin(Long userId);
    boolean isAdmin(Long userId);
}