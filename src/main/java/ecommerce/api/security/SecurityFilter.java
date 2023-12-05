package ecommerce.api.security;
import ecommerce.api.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException /*IO = Input, Output*/ {
        var authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            var token = authHeader.replace("Bearer ", "");
            var subject = tokenService.getSubject(token); //Desencripta y recupera el usuario y sus datos.
            if (subject != null){ //Cotejando a base de datos. No del tod.o necesario porque ya se corroboró al guardarse en db.
                    var user = userRepository.findByUsernameAndActive(subject, true).get();
                    var authentication = new UsernamePasswordAuthenticationToken //Genera autenticación para el usuario.
                            (user, null, user.getAuthorities()); //En caso de devolver contraseña va en "credentials".
                    SecurityContextHolder.getContext().setAuthentication(authentication); //Aplica la autenticación.
                }
            }
        filterChain.doFilter(request, response); //Sigue la cadena de filtros.
    }
}