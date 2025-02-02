package org.launchcode.demo.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.launchcode.demo.data.BookshelfRepository;
import org.launchcode.demo.data.UserRepository;
import org.launchcode.demo.models.Bookshelf;
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
    @Autowired
    BookshelfRepository bookshelfRepository;
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


    //User Login
    @GetMapping("/user/login")
    public String displayLoginForm(Model model) {
        model.addAttribute(new LoginFormDTO());
        return "user/login";
    }

    @PostMapping("/user/login")
    public String processLoginForm(@ModelAttribute @Valid LoginFormDTO loginFormDTO,
                                   Errors errors, HttpServletRequest request,
                                   Model model) {

        if (errors.hasErrors()) {
            return "user/login";
        }

        User theUser = userRepository.findByUsername(loginFormDTO.getUsername());

        if (theUser == null) {
            errors.rejectValue("username", "user.invalid", "The given username does not exist");
            return "user/login";
        }

        String password = loginFormDTO.getPassword();

        if (!theUser.isMatchingPassword(password)) {
            errors.rejectValue("password", "password.invalid", "Invalid password");
            return "user/login";
        }

        if (!theUser.getIsVerified()) {
            return "redirect:/user/verification";
        }

        setUserInSession(request.getSession(), theUser);

        return "redirect:/user/index";
    }

    @GetMapping("user/index")
    public String displayProfilePage(Model model, HttpSession session) {
        User theUser = getUserFromSession(session);
        model.addAttribute("username", theUser.getUsername());
        return "user/index";
    }


    //User Registration
    @GetMapping("/user/register")
    public String displayRegistrationForm(Model model) {
        model.addAttribute(new RegisterFormDTO());
        return "user/register";
    }

    @PostMapping("/user/register")
    public String processRegistrationForm(@ModelAttribute @Valid RegisterFormDTO registerFormDTO,
                                          Errors errors, HttpServletRequest request,
                                          Model model) {

        if (errors.hasErrors()) {
            return "user/register";
        }

        User existingUser = userRepository.findByUsername(registerFormDTO.getUsername());

        if (existingUser != null) {
            errors.rejectValue("username", "username.alreadyexists", "A user with that username already exists");
            return "user/register";
        }

        String password = registerFormDTO.getPassword();
        String verifyPassword = registerFormDTO.getVerifyPassword();
        if (!password.equals(verifyPassword)) {
            errors.rejectValue("password", "passwords.mismatch", "Passwords do not match");
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


    //Email Authentication during Registration

    @GetMapping("user/verification")
    public String displayVerificationForm(Model model) {
        model.addAttribute(new VerificationFormDTO());
        return "user/verification";
    }

    @PostMapping("user/verification")
    public String processVerificationForm(@ModelAttribute @Valid VerificationFormDTO verificationFormDTO,
                                          Errors errors, HttpServletRequest request, HttpSession session,
                                          Model model) {

        String username = verificationFormDTO.getUsername();
        User user = userRepository.findByUsername(username);

        String submittedCode = verificationFormDTO.getVerifyCode();
        String sentCode = user.getVerificationCode();

        if (!submittedCode.equals(sentCode)) {
            model.addAttribute("incorrect", "Incorrect Verification Code. Please Check Email");
            return "user/verification";
        }

        user.setIsVerified(true);
        userRepository.save(user);

        Bookshelf bookshelf = new Bookshelf(user.getUsername() + "'s Bookshelf", user);
        bookshelfRepository.save(bookshelf);

        //AJ testing something//
        setUserInSession(request.getSession(), user);
        //AJ testing//

        model.addAttribute("username", user.getUsername());

        return "user/index";

    }


    //Logout
    @GetMapping("/user/logout")
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:/user/login";
    }

    //Redirect
    @GetMapping("")
    public String redirectToLogin(){
        return "redirect:/user/login";
    }
}


