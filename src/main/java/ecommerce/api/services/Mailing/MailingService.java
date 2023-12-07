package ecommerce.api.services.Mailing;
import ecommerce.api.entities.Email.EmailDTO;
import ecommerce.api.entities.Email.EmailFileDTO;

public interface MailingService {

    void sendEmail(EmailDTO emailDTO) throws Exception;
    void sendEmailWithFile(EmailFileDTO emailFileDTO) throws Exception;
}
