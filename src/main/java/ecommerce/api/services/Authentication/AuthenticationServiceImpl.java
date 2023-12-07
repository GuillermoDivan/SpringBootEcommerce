package ecommerce.api.services.Authentication;
import ecommerce.api.entities.User.Role;
import ecommerce.api.entities.User.User;
import ecommerce.api.entities.User.UserSaveData;
import ecommerce.api.repositories.UserRepository;
import ecommerce.api.security.JWTTokenData;
import ecommerce.api.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserRepository userRepository; //Convendría usar service. Se optó por usar repository para tener acceso al atributo "active", no disponible en los dtos que trabaja el service.


    public JWTTokenData authenticate(UserSaveData userSaveData) {
        var user = userRepository.findByUsernameAndActive(userSaveData.username(), true).get();
        Authentication authToken = new UsernamePasswordAuthenticationToken(userSaveData.username(),
                    userSaveData.password());
            var authenticatedUser = authenticationManager.authenticate(authToken);
            var JWTToken = tokenService.generateToken((User) authenticatedUser.getPrincipal());
            return new JWTTokenData(JWTToken);
        }

    public boolean isSelforAdmin(Long userId) {
        var loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (loggedUser.getId() == userId || loggedUser.getRole() == Role.ADMIN);
    }

    public boolean isAdmin(Long userId) {
        var loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (loggedUser.getRole() == Role.ADMIN);
    }
}