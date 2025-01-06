package org.launchcode.demo.data;
import org.launchcode.demo.models.email.UserVerification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserVerificationRepository extends JpaRepository<UserVerification, Long> {
    UserVerification findByEmail(String email);
}

//this repository stores verification data