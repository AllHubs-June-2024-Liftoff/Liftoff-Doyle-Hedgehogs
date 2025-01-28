package org.launchcode.demo.controllers;

import org.launchcode.demo.data.BookshelfVolumeRepository;

import org.launchcode.demo.data.UserRepository;
import org.launchcode.demo.models.ApiActions;
import org.launchcode.demo.models.Volume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;

@Controller
@RequestMapping("book")
public class AddBookController {

    @GetMapping("addBook")
    public String search(Model model) {
        model.addAttribute("title", "Add a New Book to the Library");
        return "book/addBook";
    }

    @PostMapping("addBook/results")
    public String searchResults(Model model, @RequestParam String searchTerm) throws IOException {
        model.addAttribute("title", "Add a New Book to the Library");
        String rawResults = ApiActions.ApiSearch(searchTerm);
        ArrayList<Volume> parsedResults = (ApiActions.ParseResults(rawResults));
        model.addAttribute("searchResults", parsedResults);
        return "book/addBook";
    }
}
