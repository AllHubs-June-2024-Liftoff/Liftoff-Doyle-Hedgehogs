package org.launchcode.demo.data;
import org.launchcode.demo.models.VerifyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationRepository extends JpaRepository<VerifyUser, Long> {
    VerifyUser findByEmail(String email);
}

//this repository stores verification data