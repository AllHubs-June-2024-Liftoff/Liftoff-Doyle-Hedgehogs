package org.launchcode.demo.models.email;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailService  {

    @Autowired
    private JavaMailSender javaMailSender;
    //inject instance of our mailsender

    @Value("${spring.mail.username}")
    private String sender;

    public boolean sendMail(EmailDetails details) {


        try {

            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMessage());
            mailMessage.setSubject(details.getSubject());

            javaMailSender.send(mailMessage);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

