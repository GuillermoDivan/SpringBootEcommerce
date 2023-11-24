package ecommerce.api.controllers;
import ecommerce.api.services.Client.ClientService;
import ecommerce.api.entities.Client.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    //@Autowired
    private final ClientService clientService;

    @PostMapping("/save")
    @Transactional
    public ResponseEntity<ClientShowData> saveClient(@RequestBody @Valid ClientSaveData clientSaveData){
        var client = this.clientService.saveClient(clientSaveData);
        return ResponseEntity.ok(client);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ClientShowData> findClientByIdByActive(@PathVariable Long id) {
        try {
            var client = this.clientService.findClientById(true, id);
             return ResponseEntity.ok(client);
        } catch (Exception e) {  return ResponseEntity.status(HttpStatus.NOT_FOUND).build();}
    }

    @GetMapping("/inactive/id/{id}")
    public ResponseEntity<ClientShowData> findClientByIdByInactive(@PathVariable Long id) {
        try {
            var client = this.clientService.findClientById(false, id);
            return ResponseEntity.ok(client);
        } catch (Exception e) {  return ResponseEntity.status(HttpStatus.NOT_FOUND).build();}
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ClientShowData> findClientByNameByActive(@PathVariable String name) {
        try {
            var client = this.clientService.findClientByName(true, name);
            return ResponseEntity.ok(client);
        } catch (Exception e) {  return ResponseEntity.status(HttpStatus.NOT_FOUND).build();}
    }

    @GetMapping("/inactive/name/{name}")
    public ResponseEntity<ClientShowData> findClientByNameByInactive(@PathVariable String name) {
        try {
            var client = this.clientService.findClientByName(false, name);
            return ResponseEntity.ok(client);
        } catch (Exception e) {  return ResponseEntity.status(HttpStatus.NOT_FOUND).build();}
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ClientShowData> findClientByEmailByActive(@PathVariable String email) {
        try {
            var client = this.clientService.findClientByEmail(true, email);
            return ResponseEntity.ok(client);
        } catch (Exception e) {  return ResponseEntity.status(HttpStatus.NOT_FOUND).build();}
    }

    @GetMapping("/inactive/email/{email}")
    public ResponseEntity<ClientShowData> findClientByEmailByInactive(@PathVariable String email) {
        try {
            var client = this.clientService.findClientByEmail(false, email);
            return ResponseEntity.ok(client);
        } catch (Exception e) {  return ResponseEntity.status(HttpStatus.NOT_FOUND).build();}
    }

    @GetMapping("/all")
    ResponseEntity<Page<ClientShowData>> findClientListByActive (@PageableDefault(size = 3) Pageable paging) {
        return ResponseEntity.ok(clientService.findAllClientsByActive(true, paging));
    }

    @GetMapping("/inactive/all")
    ResponseEntity<Page<ClientShowData>> findClientListByInactive (@PageableDefault(size = 3) Pageable paging) {
        return ResponseEntity.ok(clientService.findAllClientsByActive(false, paging));
    }

    @PutMapping("/update/{id}")
    @Transactional
    public ResponseEntity<ClientShowData> updateClient(@RequestBody @Valid ClientUpdateData clientUpdateData){
        var result = this.clientService.updateClient(clientUpdateData);
        if (result != null) { return ResponseEntity.ok(result);}
        else {return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();}
    }

    @PutMapping("/hide/{id}")
    @Transactional
    public ResponseEntity<ClientShowData> hideClient(@PathVariable Long id){
        var result = this.clientService.hideClient(id);
        if (result) { return ResponseEntity.ok().build();}
        else {return ResponseEntity.status(HttpStatus.NOT_FOUND).build();}
    }

    @PutMapping("/reactive/{id}")
    @Transactional
    public ResponseEntity<ClientShowData> reactiveClient(@PathVariable Long id){
        var result = this.clientService.reactiveClient(id);
        if (result) { return ResponseEntity.ok().build();}
        else {return ResponseEntity.status(HttpStatus.NOT_FOUND).build();}
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<?> toggleClient(@PathVariable Long id){
        var toggledClient = clientService.toggleClient(id);
        if (toggledClient) { return ResponseEntity.ok(toggledClient);
        }  else { return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
