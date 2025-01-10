package org.launchcode.demo.controllers;

import jakarta.validation.Valid;
import org.launchcode.demo.data.BookshelfRepository;
import org.launchcode.demo.data.BookshelfVolumeRepository;
import org.launchcode.demo.data.TagRepository;
import org.launchcode.demo.data.UserRepository;
import org.launchcode.demo.models.Bookshelf;
import org.launchcode.demo.models.BookshelfVolume;
import org.launchcode.demo.models.Tag;
import org.launchcode.demo.models.User;
import org.launchcode.demo.models.dto.BookshelfVolumeTagDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


//Not sure if this is needed with authentication controller handling this now?????


@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public BookshelfVolumeRepository bookshelfVolumeRepository;

    @Autowired
    public BookshelfRepository bookshelfRepository;

    @Autowired
    TagRepository tagRepository;

    @GetMapping("/add")
    public String displayAddUserForm(Model model) {
        model.addAttribute(new User());
        return "user/add";
    }

//    @PostMapping
//    public String processAddUserForm(Model model, @Valid @ModelAttribute User user, Errors errors, String verify) {
//        model.addAttribute(new User());
//        if(errors.hasErrors()) {
//            return "user";
//        }
//        if (user.getPassword().equals(verify)) {
//            return "user/index";
//        }
//        else {
//            model.addAttribute("error", "Passwords do not match");
//            return "user/add";
//        }
//
//    }

//    @GetMapping("/login")
//    public String displayLoginForm(Model model) {
//        model.addAttribute(new User());
//        model.addAttribute("title", "Log In");
//        return "login";
//    }

    @GetMapping("/library/{username}")
    public String displayUserLibrary(@PathVariable String username, Model model){
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByUsername(username));
        User theUser = optionalUser.get();
        Bookshelf theBookshelf = bookshelfRepository.findByUser(theUser);

        model.addAttribute("title", theUser.getUsername() + "'s Library");
        model.addAttribute("bookshelfVolumes", bookshelfVolumeRepository.findByBookshelfId(theBookshelf.id));

        return "user/library";
    }

    @GetMapping("/booktags/{id}")
    public String displayBookTagDetailForm(@PathVariable Integer id, Model model){
        Optional<BookshelfVolume> bookshelfVolume = bookshelfVolumeRepository.findById(id);
        BookshelfVolume theBook = bookshelfVolume.get();
        List<Tag> currentTags = theBook.getTags();
        Iterable<Tag> allTags = tagRepository.findAllByOrderByNameAsc();
        List<Tag> notCurrentTags = new ArrayList<>();
        for (Tag tag : allTags){
            if (!currentTags.contains(tag)){
                notCurrentTags.add(tag);
            }
        }
        model.addAttribute("title", "Tags for " + theBook.volume.title + " by " + theBook.volume.author);
        model.addAttribute("bookshelfVolume", theBook);
        model.addAttribute("currentTags", theBook.getTags());
        model.addAttribute("notCurrentTags", notCurrentTags);

        return "user/booktags";
    }

    //todo: make this method work:

    @PostMapping("/booktags/{id}")
    public String processBookTagDetailForm(@PathVariable Integer id, @ModelAttribute @Valid BookshelfVolumeTagDTO bookshelfVolumeTag, Model model){
        Tag tag = bookshelfVolumeTag.getTag();
        BookshelfVolume bookshelfVolume = bookshelfVolumeTag.getBookshelfVolume();
        model.addAttribute("bookshelfVolumeId", id);
        if (!bookshelfVolume.getTags().contains(tag)) {
            bookshelfVolume.addTag(tag);
            bookshelfVolumeRepository.save(bookshelfVolume);
        }
        return "redirect:user/library";
    }

}