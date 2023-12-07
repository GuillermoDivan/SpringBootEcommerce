package ecommerce.api.services.Mailing;
import ecommerce.api.entities.Email.EmailDTO;
import ecommerce.api.entities.Email.EmailFileDTO;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class MailingServiceImpl implements MailingService{

    @Value("${spring.mail.username}")
    private String emailSender;

    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(EmailDTO emailDTO) throws Exception {
        try{
            SimpleMailMessage mail1 = new SimpleMailMessage();
            mail1.setFrom(emailSender);
            mail1.setTo(emailDTO.to());
            mail1.setSubject(emailDTO.subject());
            mail1.setText(emailDTO.message());
            mailSender.send(mail1); }
        catch (Exception e) {
            System.out.println(e.getMessage()); throw e; }
    }

    @Override
    public void sendEmailWithFile(EmailFileDTO emailFileDTO) throws Exception {
        try {
            MimeMessage mail2 = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail2, true, StandardCharsets.UTF_8.name());
            helper.setFrom(emailSender);
            helper.setTo(emailFileDTO.to());
            helper.setSubject(emailFileDTO.subject());
            helper.setText(emailFileDTO.message());
            helper.addAttachment(emailFileDTO.file().getName(), emailFileDTO.file());
            mailSender.send(mail2);
        } catch (Exception e)  {
            System.out.println(e.getMessage()); throw e; }
    }

    /*
    @Override
    public boolean sendEmail(String from, String to) throws Exception {
        try{
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject("Asunto del correo");
        message.setText("Este es un correo automático!");
        mailSender.send(message);
        return true;}
        catch (Exception e) { e.getMessage(); throw e; }
    }*/

}
