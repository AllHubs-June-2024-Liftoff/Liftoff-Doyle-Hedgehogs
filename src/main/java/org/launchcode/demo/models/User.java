package org.launchcode.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;
import java.util.stream.Collectors;

@Entity
public class User extends AbstractEntity implements UserDetails {
    private static final String AUTHORITIES_DELIMITER = "::";

    @NotBlank
    @Size(min = 3, max = 10)
    private String username;

    @Email
    private String email;

    @ManyToOne
    private Location location;

    @NotNull
    private String pwHash;

    private String verificationCode;

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private String authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return Arrays.stream(this.authorities.split(AUTHORITIES_DELIMITER))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public User() {}

    public User(String username, String email, Location location, String password, String authorities) {
        this();
        this.username = username;
        this.email = email;
        this.location = location;
        this.pwHash = encoder.encode(password);
        this.authorities = authorities;
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

    public Location getLocation() {return location;}

    public void setLocation(Location location) {this.location = location;}


    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }

    //Satifies requirement of UserDetails interface without passing private data//
    public String getPassword(){
        return null;
    }

    public void setAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(this.authorities));
    }
}