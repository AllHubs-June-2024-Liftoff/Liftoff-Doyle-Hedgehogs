package org.launchcode.demo.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.launchcode.demo.data.UserRepository;
import org.launchcode.demo.models.User;
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

    @GetMapping("user/verification")
    public String displayVerificationForm(Model model) {
        model.addAttribute(new VerificationFormDTO());
        return "user/verification";
    }
    @PostMapping("user/verification")
    public String processVerificationForm(@ModelAttribute @Valid VerificationFormDTO verificationFormDTO,
                                          Errors errors, HttpServletRequest request,
                                          Model model) {
        String username = verificationFormDTO.getUsername();
        User user = userRepository.findByUsername(username);
        String submittedCode = verificationFormDTO.getVerifyCode();
        String sentCode = user.getVerificationCode();
        if (!submittedCode.equals(sentCode)) {
            ///errors.rejectValue("code", "incorrect code", "Codes do not match");
            model.addAttribute("incorrect", "Incorrect Verification Code. Please Check Email");
            return "user/verification";
        }

        user.setIsVerified(true);
        userRepository.save(user);

        model.addAttribute("username", user.getUsername());

        return "user/index";

    }
}
