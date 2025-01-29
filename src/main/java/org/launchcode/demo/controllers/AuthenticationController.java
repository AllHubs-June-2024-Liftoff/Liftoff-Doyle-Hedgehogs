package org.launchcode.demo.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.launchcode.demo.data.UserRepository;
import org.launchcode.demo.models.User;
import org.launchcode.demo.models.dto.LoginFormDTO;
import org.launchcode.demo.models.dto.RegisterFormDTO;
import org.launchcode.demo.models.dto.UserEmailDTO;
import org.launchcode.demo.models.dto.VerificationFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.launchcode.demo.models.email.EmailService;
import org.launchcode.demo.models.email.EmailDetails;

import java.util.List;
import java.util.Optional;

import org.launchcode.demo.models.email.VerificationCodeGenerator;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthenticationController {

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

    @GetMapping("/user/login")
    public String displayLoginForm(Model model) {
        model.addAttribute(new LoginFormDTO());
        model.addAttribute("title", "Log In");
        return "user/login";
    }

    @PostMapping("/user/login")
    public String processLoginForm(@ModelAttribute @Valid LoginFormDTO loginFormDTO,
                                   Errors errors, HttpServletRequest request,
                                   Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Log In");
            return "user/login";
        }

        User theUser = userRepository.findByUsername(loginFormDTO.getUsername());

        if (theUser == null) {
            errors.rejectValue("username", "user.invalid", "The given username does not exist");
            model.addAttribute("title", "Log In");
            return "user/login";
        }

        String password = loginFormDTO.getPassword();

        if (!theUser.isMatchingPassword(password)) {
            errors.rejectValue("password", "password.invalid", "Invalid password");
            model.addAttribute("title", "Log In");
            return "user/login";
        }

        if (!theUser.getIsVerified()) {
            return "redirect:/user/verification";
        }

        model.addAttribute("username", theUser.getUsername());

        setUserInSession(request.getSession(), theUser);

        return "/user/index";
    }

    @GetMapping("/user/register")
    public String displayRegistrationForm(Model model) {
        model.addAttribute(new RegisterFormDTO());
        model.addAttribute("title", "Register");
        return "user/register";
    }

    @PostMapping("/user/register")
    public String processRegistrationForm(@ModelAttribute @Valid RegisterFormDTO registerFormDTO,
                                          Errors errors, HttpServletRequest request,
                                          Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Register");
            List<ObjectError> theseErrors = errors.getAllErrors();
            for (ObjectError error : theseErrors) {
                System.out.println(error.toString());
            }
            return "user/register";
        }

        User existingUser = userRepository.findByUsername(registerFormDTO.getUsername());

        if (existingUser != null) {
            errors.rejectValue("username", "username.alreadyexists", "A user with that username already exists");
            model.addAttribute("title", "Register");
            return "user/register";
        }

        String password = registerFormDTO.getPassword();
        String verifyPassword = registerFormDTO.getVerifyPassword();
        if (!password.equals(verifyPassword)) {
            errors.rejectValue("password", "passwords.mismatch", "Passwords do not match");
            model.addAttribute("title", "Register");
            return "user/register";
        }

        User newUser = new User(registerFormDTO.getUsername(), registerFormDTO.getEmail(), registerFormDTO.getLocation(), registerFormDTO.getPassword());
        String code = VerificationCodeGenerator.createVerificationCode();
        newUser.setVerificationCode(code);
        userRepository.save(newUser);
        setUserInSession(request.getSession(), newUser);
        model.addAttribute("user", newUser.getUsername());
        EmailDetails theseDetails = new EmailDetails(registerFormDTO.getEmail(), "Your verification code is: " + code, "Email Verification Code");
        sendUserMail(theseDetails);
        return "redirect:/user/verification";
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





    @GetMapping("/user/email")
    public String displayUserEmailForm(Model model){
        model.addAttribute(new UserEmailDTO());
        return "user/email";
    }

    @PostMapping("/user/email")
    public String processUserEmailForm(@ModelAttribute @Valid UserEmailDTO userEmailDTO, @RequestParam(value="book") String book, @RequestParam(value="recipient") String recipient,
                                          Errors errors, HttpServletRequest request,
                                          Model model) {

        String email = userEmailDTO.getEmail();

        EmailDetails theseDetails = new EmailDetails(recipient, "Hello! A user is interested in " + book + ". To confirm swap details, please email " + email, "Someone Is Interested in Your Book!");
        sendUserMail(theseDetails);

        model.addAttribute("success", "Email sent!");
        return "user/email";
    }



//    @PostMapping("user/email")
//    public String processUserEmailForm(){
//        EmailDetails theseDetails = new EmailDetails("", "Hello! A user is interested in your book. To contact, please email NAME", "Someone Is Interested in Your Book!");
//        sendUserMail(theseDetails);
//        return "user/email";
//    }

    @GetMapping("/user/logout")
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:/user/login";
    }

}


