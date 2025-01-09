//package org.launchcode.demo.controllers;
//
//import jakarta.servlet.http.HttpSession;
//import org.launchcode.demo.data.UserRepository;
//import org.launchcode.demo.models.User;
//import org.launchcode.demo.models.email.EmailService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.Optional;
//
//@Controller
//@RequestMapping("user/verification")
//public class VerificationController {
//
//    @Autowired
//    UserRepository userRepository;
//    @Autowired private EmailService emailService;
//
//    private static final String userSessionKey = "user";
//
//    public User getUserFromSession(HttpSession session) {
//        Integer userId = (Integer) session.getAttribute(userSessionKey);
//        if (userId == null) {
//            return null;
//        }
//
//        Optional<User> user = userRepository.findById(userId);
//
//        if (user.isEmpty()) {
//            return null;
//        }
//
//        return user.get();
//    }
//
//    private static void setUserInSession(HttpSession session, User user) {
//        session.setAttribute(userSessionKey, user.getId());
//    }
//
//    @PostMapping
//
//
//
//}
