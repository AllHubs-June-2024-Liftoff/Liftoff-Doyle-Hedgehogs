package org.launchcode.demo.models.email;
import org.launchcode.demo.data.UserRepository;
import org.launchcode.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationService {

    @Autowired
    private UserRepository userRepository;

    public boolean verifyCode(String username, String code) {
        User verifyUser = userRepository.findByUsername(username);
        if (verifyUser != null && verifyUser.getVerificationCode().equals(code)) {
            return true;
        }
        return false;
    }
}

//this class compares and validates the submitted code with the stored database code
