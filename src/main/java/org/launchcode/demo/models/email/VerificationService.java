package org.launchcode.demo.models.email;
import org.launchcode.demo.data.UserVerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationService {

    @Autowired
    private UserVerificationRepository verificationRepository;

    public boolean verifyCode(String email, String code) {
        UserVerification verifyUser = verificationRepository.findByEmail(email);
        if (verifyUser != null && verifyUser.getCode().equals(code)) {
            return true;
        }
        return false;
    }
}

//this class compares and validates the submitted code with the stored database code
