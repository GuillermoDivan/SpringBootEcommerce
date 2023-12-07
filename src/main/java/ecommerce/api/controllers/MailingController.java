package ecommerce.api.controllers;
import ecommerce.api.entities.Email.EmailDTO;
import ecommerce.api.entities.Email.EmailFileDTO;
import ecommerce.api.services.Mailing.MailingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mailing")
@RequiredArgsConstructor
public class MailingController {

    private final MailingService mailingService;
    private final JavaMailSender mailSender;

    @PostMapping("/m1")
    public ResponseEntity<?> sendEmail(@RequestBody EmailDTO emailDTO)  {
        try{
            this.mailingService.sendEmail(emailDTO);
            return ResponseEntity.ok().build();}
        catch (Exception e) { return ResponseEntity.internalServerError().build();}
    }

    @PostMapping("/m2")
    public ResponseEntity<?> sendEmailFile(@ModelAttribute EmailFileDTO emailFileDTO)  {
        try{
            this.mailingService.sendEmailWithFile(emailFileDTO);
            return ResponseEntity.ok().build();}
        catch (Exception e) { return ResponseEntity.internalServerError().build();}
    }

}