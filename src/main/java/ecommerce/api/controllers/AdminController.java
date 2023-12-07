package ecommerce.api.controllers;
import ecommerce.api.entities.User.*;
import ecommerce.api.services.User.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@PostAuthorize("hasRole('ADMIN')")
//@PostAuthorize en lugar de PreAuthorize porque fue programado de un modo que
//el rol no es tenido en cuenta en el token, entonces necesita loggearse primero
//para luego recuperar su rol.
public class AdminController {

    private final UserService userService;

    @GetMapping("/inactive/id/{id}")
    public ResponseEntity<UserShowData> findByIdAndActive(@PathVariable Long id, boolean active) {
        return ResponseEntity.ok(userService.findByIdAndActive(id, false));
    }

    @GetMapping("/inactive/{username}")
    public ResponseEntity<UserShowData> findByUsernameAndActive(@PathVariable
                                                                String username, boolean active) {
        return ResponseEntity.ok(userService.findByUsernameAndActive(username, false));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<UserShowData>> findUserList(@PageableDefault(size = 10)
                                                           Pageable paging) {
        return ResponseEntity.ok(userService.findAllByActive(true, paging));
    }

    @GetMapping("/all/inactive")
    @PostAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserShowData>> findInactiveUserList
            (@PageableDefault(size = 10) Pageable paging) {
        return ResponseEntity.ok(userService.findAllByActive(false, paging));
    }

    @DeleteMapping("/reactivate/{id}")
    @Transactional
    public ResponseEntity<Boolean> reactivatePatient(@PathVariable Long id) {
        try {
            boolean reactivated = userService.reactivateUser(id);
            if (reactivated) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }
        catch(EntityNotFoundException e){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/id/{id}")
    @Transactional
    public ResponseEntity<Boolean> deleteUserFromDatabase(@PathVariable Long id) {
        try {
            boolean deleted = userService.deleteUser(id);
            if (deleted) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
