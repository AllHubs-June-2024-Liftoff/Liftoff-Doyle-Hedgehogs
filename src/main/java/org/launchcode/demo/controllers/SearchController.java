package org.launchcode.demo.controllers;

import org.launchcode.demo.data.BookshelfVolumeRepository;
import org.launchcode.demo.data.LocationRepository;
import org.launchcode.demo.data.UserRepository;
import org.launchcode.demo.models.LibraryData;
import org.launchcode.demo.models.BookshelfVolume;
import org.launchcode.demo.models.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@Controller
@RequestMapping("search")
public class SearchController {

    @Autowired
    private BookshelfVolumeRepository bookshelfVolumeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationRepository locationRepository;

    static HashMap<String, String> columnOptions = new HashMap<>();

    static HashMap<Integer, String> locationOptions = new HashMap<>();

    public SearchController(){
        columnOptions.put("title", "Title");
        columnOptions.put("author", "Author");
    }


    public HashMap<Integer, String> getAllLocations(){
        Iterable<Location> allLocations = locationRepository.findAll();
        for (Location location : allLocations){
            locationOptions.put(location.getId(), location.getName());
        }
        return locationOptions;
    }


    @RequestMapping("")
    public String search(Model model){

        model.addAttribute("columns", columnOptions);
        model.addAttribute("locations", getAllLocations());
        return "search";
    }

    @PostMapping("results")
    public String displaySearchResults(Model model, @RequestParam (required = false) String location, @RequestParam (required = false) String searchType, @RequestParam String searchTerm){
        if (location == null){
            model.addAttribute("errorMessage", "Please select a location");
            model.addAttribute("columns", columnOptions);
            model.addAttribute("locations", getAllLocations());
            return "search";

        } else if (searchType == null) {
            model.addAttribute("errorMessage", "Please select a search category");
            model.addAttribute("columns", columnOptions);
            model.addAttribute("locations", getAllLocations());
            return "search";
        } else if (searchTerm == null){
            Iterable<BookshelfVolume> booksByLocation = LibraryData.filterByLocation(location, bookshelfVolumeRepository.findAll());
            return "search";
        } else {
            Iterable<BookshelfVolume> bookshelfVolumes;
            Iterable<BookshelfVolume> booksByLocation = LibraryData.filterByLocation(location, bookshelfVolumeRepository.findAll());
            bookshelfVolumes = LibraryData.findByColumnAndValue(searchType, searchTerm, booksByLocation);

            model.addAttribute("locations", getAllLocations());
            model.addAttribute("columns", columnOptions);
            model.addAttribute("title", "Books in the " + location + " area with " +
                    columnOptions.get(searchType) + " containing: " + searchTerm);
            model.addAttribute("bookshelfVolumes", bookshelfVolumes);
        }
        return "search";
    }

    @GetMapping("/bookshelf/{id}")
    public String bookshelf(String id){
        return "redirect:";
    }

}
