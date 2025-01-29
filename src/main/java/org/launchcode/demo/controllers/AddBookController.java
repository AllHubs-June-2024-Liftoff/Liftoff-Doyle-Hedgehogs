package org.launchcode.demo.controllers;

import jakarta.servlet.http.HttpSession;

import org.launchcode.demo.data.VolumeRepository;
import org.launchcode.demo.models.ApiActions;
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

    @GetMapping("addBook")
    public String search(Model model) {
        model.addAttribute("title", "Add a New Book to the Library");
        return "book/addBook";
    }

    @PostMapping("api/results")
    public String handleAPISearchPostRequest(@RequestParam String searchTerm, HttpSession session) throws IOException {
        String rawResults = ApiActions.ApiSearch(searchTerm);
        Object parsedResults = new ArrayList<Volume>(ApiActions.ParseResults(rawResults));
        session.setAttribute("parsedResults", parsedResults);
        return "redirect:../addBook/results";
    }


    @GetMapping("/addBook/results")
    public String displaySearchResults(Model model, HttpSession session) {
        model.addAttribute("title", "Add a New Book to the Library");
        Object parsedResults = session.getAttribute("parsedResults");
        model.addAttribute("searchResults", parsedResults);
        return "book/addBook/results";
    }

    @PostMapping("/api/results/selected")
    public ModelAndView passVolumeDataFromSearchResult(@RequestParam(value="author") String author, HttpSession session) throws IOException {
        session.setAttribute("author", author);

        return new ModelAndView("redirect:/volume/add");
    }

}
