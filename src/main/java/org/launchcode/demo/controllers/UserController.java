package org.launchcode.controllers;

import jakarta.validation.Valid;
import org.launchcode.demo.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("user")
public class UserController {

    @GetMapping("/add")
    public String displayAddUserForm(Model model) {
        model.addAttribute(new User());
        return "user/add";
    }

//    @PostMapping
//    public String processAddUserForm(Model model, @Valid @ModelAttribute User user, Errors errors, String verify) {
//        model.addAttribute(new User());
//        if(errors.hasErrors()) {
//            return "user";
//        }
//        if (user.getPassword().equals(verify)) {
//            return "user/index";
//        }
//        else {
//            model.addAttribute("error", "Passwords do not match");
//            return "user/add";
//        }
//
//    }

    @GetMapping("/login")
    public String displayLoginForm(Model model) {
        model.addAttribute(new User());
        model.addAttribute("title", "Log In");
        return "login";
    }

}
