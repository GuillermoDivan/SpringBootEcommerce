package ecommerce.api.controllers;
import ecommerce.api.entities.User.*;
import ecommerce.api.services.User.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/save")
    @Transactional
    public ResponseEntity<UserShowData> createUser(@RequestBody @Valid
                                                       UserSaveData userSaveData){
        var dto = this.userService.saveUser(userSaveData);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserShowData> findByIdAndActive(@PathVariable Long id, boolean active) {
        var result = userService.findByIdAndActive(id, true);
        if (result == null) { return ResponseEntity.badRequest().build(); }
        else { return ResponseEntity.ok().body(result); }
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserShowData> findByUsernameAndActive(@PathVariable
                                                       String username, boolean active) {
        return ResponseEntity.ok(userService.findByUsernameAndActive(username, true));
    }

    @PutMapping("/id/{id}")
    @Transactional
    public ResponseEntity<UserShowData> updateUser(@RequestBody @Valid
                                               UserUpdateData userUpdateData, boolean active) {
        var result = this.userService.updateUser(userUpdateData, true);
        if (result != null) { return ResponseEntity.ok(this.userService.updateUser(userUpdateData, true)); }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/hide/{id}")
    @Transactional
    public ResponseEntity<Boolean> turnOffUser(@PathVariable Long id){
        boolean turnedOff = userService.turnOffUser(id);
        if (turnedOff) { return ResponseEntity.noContent().build(); }
        else { return ResponseEntity.badRequest().build(); }
    }

}
