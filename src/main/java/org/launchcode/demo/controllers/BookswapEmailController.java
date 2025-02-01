package org.launchcode.demo.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.launchcode.demo.data.UserRepository;
import org.launchcode.demo.models.User;
import org.launchcode.demo.models.dto.UserEmailDTO;
import org.launchcode.demo.models.dto.VerificationFormDTO;
import org.launchcode.demo.models.email.EmailDetails;
import org.launchcode.demo.models.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class BookswapEmailController {

        @Autowired
        UserRepository userRepository;
        @Autowired private EmailService emailService;

        private static final String userSessionKey = "user";

        public User getUserFromSession(HttpSession session) {
            Integer userId = (Integer) session.getAttribute(userSessionKey);
            if (userId == null) {
                return null;
            }

            Optional<User> user = userRepository.findById(userId);

            if (user.isEmpty()) {
                return null;
            }

            return user.get();
        }

        private static void setUserInSession(HttpSession session, User user) {
            session.setAttribute(userSessionKey, user.getId());
        }



        public boolean
        sendUserMail(EmailDetails details)
        {
            boolean status
                    = emailService.sendMail(details);

            return status;
        }

    @GetMapping("/user/email")
    public String displayUserEmailForm(Model model){
        model.addAttribute(new UserEmailDTO());
        return "user/email";
    }

    @PostMapping("/user/email")
    public String processUserEmailForm(@ModelAttribute @Valid UserEmailDTO userEmailDTO, @RequestParam(value="book") String book, @RequestParam(value="recipient") String recipient,
                                       Errors errors, HttpServletRequest request, Model model) {

        String email = userEmailDTO.getEmail();

        EmailDetails theseDetails = new EmailDetails(recipient, "Hello! A user is interested in " + book + ". To confirm swap details, please email " + email, "Someone Is Interested in Your Book!");
        sendUserMail(theseDetails);

        model.addAttribute("success", "Email sent!");
        return "user/email";
    }

}
