package org.launchcode.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
public class User {

    @Id
    @GeneratedValue
    private int id;

    @NotBlank
    @Size(min = 3, max = 10)
    private String username;

    @Email
    private String email;

    @NotBlank
    private Integer location;

    @NotNull
    private String pwHash;

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User() {}

    public User(String username, String email, Integer location, String password) {
        this();
        this.username = username;
        this.email = email;
        this.location = location;
        this.pwHash = encoder.encode(password);
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {this.email = email;}

    public Integer getLocation() {return location;}

    public void setLocation(Integer location) {this.location = location;}


    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }

}


