package ecommerce.api.controllers;
import ecommerce.api.entities.User.UserSaveData;
import ecommerce.api.services.Authentication.AuthenticationService;
import ecommerce.api.security.JWTTokenData;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<JWTTokenData> authenticateUser(@RequestBody @Valid
                                           UserSaveData userSaveData) {
        try {
            JWTTokenData JWT = authenticationService.authenticate(userSaveData);
            if (JWT != null) { return ResponseEntity.ok(JWT); }
            else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (EntityNotFoundException notFound) { return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); }
        catch (Exception others) { return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); }
        }

    }