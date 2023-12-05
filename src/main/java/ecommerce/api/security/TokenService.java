package ecommerce.api.security;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import ecommerce.api.entities.User.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.secret}") //Conecta a ApplicationProperties para tomar la key de encriptado desde una ubicación segura.
    private String apiSecret;
    @Value("${api.security.issuer}")
    private String apiIssuer;

    public String generateToken(User user){
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer(apiIssuer)
                    .withSubject(user.getUsername()) //User.
                    .withClaim("id", user.getId()) //Encadena info necesaria, ej username o mail.
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException();
        }
    }

    public String getSubject(String token) {
        if (token == null) {throw new RuntimeException();}
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            DecodedJWT verifier = JWT.require(algorithm)
                    .withIssuer(apiIssuer)
                    .build()
                    .verify(token);
            if (verifier.getSubject() == null) { throw new RuntimeException("Invalid verifier.");}
            return verifier.getSubject();
        } catch (JWTVerificationException e) {
            System.err.println(e.toString());
            return null;
        }
    }

    private Instant generateExpirationDate(){
        return LocalDateTime.now().plusHours(48).toInstant
                (ZoneOffset.of("-05:00"));
    }
}
