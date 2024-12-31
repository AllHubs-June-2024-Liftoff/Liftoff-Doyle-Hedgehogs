package org.launchcode.demo.models;
import org.launchcode.demo.data.VerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationAction {

    @Autowired
    private VerificationRepository verificationRepository;

    public boolean verifyCode(String email, String code) {
        VerifyUser verifyUser = verificationRepository.findByEmail(email);
        if (verifyUser != null && verifyUser.getCode().equals(code)) {
            return true;
        }
        return false;
    }
}

//this class compares and validates the submitted code with the stored database code
