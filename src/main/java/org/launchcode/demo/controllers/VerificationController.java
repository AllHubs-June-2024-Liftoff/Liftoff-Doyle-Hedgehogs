package org.launchcode.demo.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.launchcode.demo.data.UserRepository;
import org.launchcode.demo.models.User;
import org.launchcode.demo.models.dto.LoginFormDTO;
import org.launchcode.demo.models.dto.RegisterFormDTO;
import org.launchcode.demo.models.dto.VerificationFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//@RequestMapping("user")
//public class VerificationController {
//
//    @Autowired
//    UserRepository userRepository;
//
//    @GetMapping("/verification")
//    public String displayVerificationForm(Model model) {
//        model.addAttribute(new VerificationFormDTO());
//        return "user/verification";
//    }
//
//    @PostMapping("/verification")
//    public String processVerificationForm(@ModelAttribute @Valid VerificationFormDTO verificationFormDTO,
//                                          Errors errors, HttpServletRequest request,
//                                          Model model) {
//
//        String username = verificationFormDTO.getUsername();
//        User user = userRepository.findByUsername(username);
//
//        String submittedCode = verificationFormDTO.getVerifyCode();
//        String sentCode = user.getVerificationCode();
//
//
//        if (!submittedCode.equals(sentCode)) {
//            errors.rejectValue("verification code", "incorrect code", "Codes do not match");
//            model.addAttribute("title", "Register");
//            return "register";
//        }
//
//        return "index";
//
//    }
//}
