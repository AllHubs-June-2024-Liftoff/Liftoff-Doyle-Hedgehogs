package org.launchcode.demo.controllers;

// Java Program to Create Rest Controller that
// Defines various API for Sending Mail

package com.SpringBootEmail.controller;

// Importing required classes
import org.launchcode.demo.models.email.EmailService;
import org.launchcode.demo.models.email.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// Annotation
@RestController
// Class
public class EmailController {

    @Autowired private EmailService emailService;

    // Sending a simple Email
    @PostMapping("/sendMail")
    public String
    sendMail(@RequestBody EmailDetails details)
    {
        String status
                = emailService.sendMail(details);

        return status;
    }

}

