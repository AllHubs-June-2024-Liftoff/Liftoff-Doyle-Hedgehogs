package org.launchcode.demo.controllers;

import org.launchcode.demo.data.BookshelfVolumeRepository;
import org.launchcode.demo.models.LibraryData;
import org.launchcode.demo.models.BookshelfVolume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@Controller
@RequestMapping("search")
public class SearchController {

    @Autowired
    private BookshelfVolumeRepository bookshelfVolumeRepository;

    static HashMap<String, String> columnOptions = new HashMap<>();

    public SearchController(){
        columnOptions.put("title", "Title");
        columnOptions.put("author", "Author");
    }

    @RequestMapping("")
    public String search(Model model){
        model.addAttribute("columns", columnOptions);
        return "search";
    }

    @PostMapping("results")
    public String displaySearchResults(Model model, @RequestParam String searchType, @RequestParam String searchTerm){
        Iterable<BookshelfVolume> bookshelfVolumes;

        bookshelfVolumes = LibraryData.findByColumnAndValue(searchType, searchTerm, bookshelfVolumeRepository.findAll());
        model.addAttribute("columns", columnOptions);
        model.addAttribute("title", "Books with " + columnOptions.get(searchType) + "containing: " + searchTerm);
        model.addAttribute("bookshelfVolumes", bookshelfVolumes);


        return "search";
    }

    //TODO send search results to separate page with a "Back to Search" link
    //TODO link to search by tags after Tag class/controller/repository created
}
