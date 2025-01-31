package org.launchcode.demo.controllers;

import jakarta.servlet.http.HttpSession;

import org.hibernate.boot.model.source.internal.hbm.AttributesHelper;
import org.launchcode.demo.data.BookshelfRepository;
import org.launchcode.demo.data.VolumeRepository;
import org.launchcode.demo.models.ApiActions;
import org.launchcode.demo.models.Bookshelf;
import org.launchcode.demo.models.Volume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;

@Controller
@RequestMapping("book")
public class AddBookController {

    @Autowired
    VolumeRepository volumeRepository;

    @Autowired
    BookshelfRepository bookshelfRepository;

    @GetMapping("addBook")
    public String search(Model model, HttpSession session) {
        Integer bookshelfId = (Integer) session.getAttribute("bookshelfId");
        session.setAttribute("bookshelfId", bookshelfId);
        model.addAttribute("bookshelfId", bookshelfId);
        model.addAttribute("title", "Add a New Book to the Library");
        return "book/addBook";
    }

    @PostMapping("api/results")
    public String handleAPISearchPostRequest(@RequestParam String searchTerm,
//                                             @RequestParam Integer bookshelfId,
                                             HttpSession session) throws IOException {
        String rawResults = ApiActions.ApiSearch(searchTerm);
        Object parsedResults = new ArrayList<Volume>(ApiActions.ParseResults(rawResults));
        Integer bookshelfId = (Integer) session.getAttribute("bookshelfId");
        session.setAttribute("bookshelfId", bookshelfId);
        session.setAttribute("parsedResults", parsedResults);
        return "redirect:../addBook/results";
    }


    @GetMapping("/addBook/results")
    public String displaySearchResults(Model model, HttpSession session) {

        model.addAttribute("title", "Add a New Book to the Library");
        Object parsedResults = session.getAttribute("parsedResults");
        Integer bookshelfId = (Integer) session.getAttribute("bookshelfId");
        model.addAttribute("searchResults", parsedResults);
        model.addAttribute("bookshelfId", bookshelfId);
        return "book/addBook/results";
    }

    @PostMapping("/api/results/selected")
    public ModelAndView passVolumeDataFromSearchResult(String author,
                                                       String bookId,
                                                       String bookTitle, String description, String thumbnail,
                                                       HttpSession session) throws IOException {
        if (description.isEmpty()){
            description = "";
        }
        Integer bookshelfId = (Integer) session.getAttribute("bookshelfId");
        session.setAttribute("bookId", bookId);
        session.setAttribute("bookTitle", bookTitle);
        session.setAttribute("author", author);
        session.setAttribute("description", description);
        session.setAttribute("thumbnail", thumbnail);
        session.setAttribute("bookshelfId", bookshelfId);

//        return new ModelAndView("redirect:/volume/add");
        return new ModelAndView("redirect:/book/addToBookshelf");
    }

}
