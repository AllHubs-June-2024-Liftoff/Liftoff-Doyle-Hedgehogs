package org.launchcode.demo.controllers;

import jakarta.servlet.http.HttpSession;
import org.launchcode.demo.data.BookshelfRepository;
import org.launchcode.demo.data.BookshelfVolumeRepository;
import org.launchcode.demo.data.UserRepository;
import org.launchcode.demo.models.Bookshelf;
import org.launchcode.demo.models.BookshelfVolume;
import org.launchcode.demo.models.LibraryData;
import org.launchcode.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("bookshelf")
public class BookshelfController {

    @Autowired
    public BookshelfRepository bookshelfRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public BookshelfVolumeRepository bookshelfVolumeRepository;

    // User view of another user's bookshelf //
    @GetMapping("/{id}")
    public String displayLibraryBookshelf(@PathVariable Integer id, Model model){

        Optional<Bookshelf> result = bookshelfRepository.findById(id);
        Bookshelf bookshelf = result.get();
        List<BookshelfVolume> bookshelfVolumes = LibraryData.filterByHasBook(LibraryData.filterByBookshelf(bookshelf, bookshelfVolumeRepository.findAll()));
        model.addAttribute("title", "Bookshelf: " + bookshelf.getBookshelf_name());
        model.addAttribute("bookshelfVolumes", bookshelfVolumes);

        return "bookshelf/view";
    }

    // User view of their own bookshelf
    @GetMapping("/view/{username}")
    public String displayUserLibrary(@PathVariable String username, Model model, HttpSession session){
        //pull down user from session and make sure same user so only person whose bookshelf it is can edit/modify bookshelf
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByUsername(username));
        User theUser = optionalUser.get();
        //pullin bookshelf via user
        Bookshelf theBookshelf = bookshelfRepository.findByUser(theUser);
        //setting bookshelf id into session so when user adds book it adds to correct bookshelf
        //can use bookshelf id in crud operations once stored (bc can get again)
        session.setAttribute("bookshelfId", theBookshelf.getId());
        List<BookshelfVolume> bookshelfVolumes = LibraryData.filterByBookshelf(theBookshelf, bookshelfVolumeRepository.findAll());
        model.addAttribute("title", theUser.getUsername() + "'s Library");
        model.addAttribute("bookshelfVolumes", bookshelfVolumes);
        model.addAttribute("bookshelfId", theBookshelf.getId());

        return "bookshelf/library";
    }

}
