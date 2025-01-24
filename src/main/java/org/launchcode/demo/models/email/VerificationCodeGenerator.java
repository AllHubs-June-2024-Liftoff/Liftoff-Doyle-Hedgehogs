package org.launchcode.demo.models.email;
import java.security.SecureRandom;

public class VerificationCodeGenerator {
    private static final String numbers = "1234567890";
    private static final int length = 5;
    private static final SecureRandom random = new SecureRandom();

    public static String createVerificationCode() {
        StringBuilder code = new StringBuilder(length);
        for (int i=0; i < length; i++) {
            code.append(numbers.charAt(random.nextInt(numbers.length())));
        }
        return code.toString();
    }
}

//this class generates the random code
