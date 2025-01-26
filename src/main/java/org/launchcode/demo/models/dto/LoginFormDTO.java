package org.launchcode.demo.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.launchcode.demo.models.Location;


public class LoginFormDTO {

    @NotNull
    @NotBlank
    @Size(min = 3, max = 10, message = "Username must be between 3-10 characters.")
    private String username;

    @NotNull
    @NotBlank
    @Size(min = 5, max = 10, message = "Password must be between 5-10 characters")
    private String password;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
