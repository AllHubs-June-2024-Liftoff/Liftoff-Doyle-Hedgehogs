package org.launchcode.demo.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.launchcode.demo.data.BookshelfVolumeRepository;
import org.launchcode.demo.data.UserRepository;
import org.launchcode.demo.models.BookshelfVolume;
import org.launchcode.demo.models.User;
import org.launchcode.demo.models.dto.UserEmailDTO;
import org.launchcode.demo.models.dto.VerificationFormDTO;
import org.launchcode.demo.models.email.EmailDetails;
import org.launchcode.demo.models.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class BookswapEmailController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookshelfVolumeRepository bookshelfVolumeRepository;
    @Autowired private EmailService emailService;

    private static final String userSessionKey = "user";

    public User getUserFromSession(HttpSession session) {
            Integer userId = (Integer) session.getAttribute(userSessionKey);
            if (userId == null) {
                return null;
            }

            Optional<User> user = userRepository.findById(userId);

            if (user.isEmpty()) {
                return null;
            }

            return user.get();
        }

    private static void setUserInSession(HttpSession session, User user) {
            session.setAttribute(userSessionKey, user.getId());
    }

    public boolean
    sendUserMail(EmailDetails details) {

        boolean status
                = emailService.sendMail(details);

        return status;
    }

    @GetMapping("/user/email")
    public String displayUserEmailForm(Model model, HttpSession session){
        model.addAttribute(new UserEmailDTO());
        User user = getUserFromSession(session);
        String recipient = (String) session.getAttribute("recipient");
        String bookTitle = (String) session.getAttribute("bookTitle");
        Integer bookId = (Integer) session.getAttribute("bookId");
        String requestingUsername = (String) session.getAttribute("requestingUsername");
        model.addAttribute("recipient", recipient);
        model.addAttribute("bookTitle", bookTitle);
        model.addAttribute("bookId", bookId);
        model.addAttribute("requestingUsername", requestingUsername);

        return "user/email";
    }

    @PostMapping("/user/email")
    public String processUserEmailForm(@ModelAttribute @Valid UserEmailDTO userEmailDTO, @RequestParam(value="bookTitle") String bookTitle,
                                       @RequestParam(value="recipient") String recipient, @RequestParam(value="bookId") Integer bookId,
                                       Errors errors, HttpServletRequest request, HttpSession session,
                                       Model model) {
        BookshelfVolume bookshelfVolume = bookshelfVolumeRepository.findById(bookId).get();
        System.out.println(bookId);
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        User transferToUser = userRepository.findById(userId).get();
        bookshelfVolume.setHas_book(false);
        bookshelfVolume.setPendingTransferTo(transferToUser.getUsername());
        bookshelfVolumeRepository.save(bookshelfVolume);

        String email = userEmailDTO.getEmail();

        EmailDetails theseDetails = new EmailDetails(recipient, "Hello! A user is interested in " + bookTitle + ". To confirm swap details, please email " + email, "Someone Is Interested in Your Book!");
        sendUserMail(theseDetails);

        model.addAttribute("success", "Email sent!");
        return "user/emailsuccess";
    }

}
