package org.launchcode.demo.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("user_name")
    public String sessionUsername(HttpSession session) {
        Object user_name = session.getAttribute("user_name");
        return user_name != null ? user_name.toString() : null;
    }
}
