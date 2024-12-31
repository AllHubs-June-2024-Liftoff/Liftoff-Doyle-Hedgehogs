package org.launchcode.demo.models;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmail {

    @Autowired
    private JavaMailSender mailSender;

            public void sendEmailVerification(String email, String code) {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(email);
                message.setSubject("Unique Verification Code");
                message.setText("Your unique verification code is: " + code);
                mailSender.send(message);

            }

            //this class sends the unique verification code to the specific email address
}
