package org.launchcode.demo.controllers;

import jakarta.servlet.http.HttpSession;
import org.launchcode.demo.data.BookshelfVolumeRepository;
import org.launchcode.demo.data.LocationRepository;
import org.launchcode.demo.data.UserRepository;
import org.launchcode.demo.models.LibraryData;
import org.launchcode.demo.models.BookshelfVolume;
import org.launchcode.demo.models.Location;
import org.launchcode.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

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


    public HashMap<Integer, String> getAllLocations(){
        Iterable<Location> allLocations = locationRepository.findAll();
        for (Location location : allLocations){
            locationOptions.put(location.getId(), location.getName());
        }
        return locationOptions;
    }


    @RequestMapping("")
    public String search(Model model, HttpSession session){

        model.addAttribute("columns", columnOptions);
        model.addAttribute("locations", getAllLocations());
        model.addAttribute("title", "Search the Little Online Library");
        return "search";
    }

    @PostMapping("redirectToResults")
    public String redirectToSearchResults(Model model, @RequestParam (required = false) String location,
                                          @RequestParam (required = false) String searchType, @RequestParam
                                           String searchTerm, HttpSession session) throws IOException {
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
//            session.setAttribute("");
            return "redirect:../search/results";
        } else {
            Iterable<BookshelfVolume> bookshelfVolumes;
            Iterable<BookshelfVolume> booksByLocation = LibraryData.filterByLocation(location, bookshelfVolumeRepository.findAll());
            bookshelfVolumes = LibraryData.findByColumnAndValue(searchType, searchTerm, booksByLocation);

            session.setAttribute("bookshelfVolumes", bookshelfVolumes);
            session.setAttribute("searchTerm", searchTerm);
            session.setAttribute("searchType", searchType);
            session.setAttribute("location", location);
        }
        return "redirect:../search/results";
    }

    @GetMapping("results")
    public String displayLibrarySearchResults(Model model, HttpSession session){
        User user = getUserFromSession(session);
        Iterable bookshelfVolumes = (Iterable) session.getAttribute("bookshelfVolumes");
        String searchTerm = (String) session.getAttribute("searchTerm");
        String searchType = (String) session.getAttribute("searchType");
        String location = (String) session.getAttribute("location");
        model.addAttribute("title", "Search the Little Online Library");
        model.addAttribute("locations", getAllLocations());
        model.addAttribute("columns", columnOptions);
        model.addAttribute("title", "Books in the " + location + " area with " +
                columnOptions.get(searchType) + " containing: " + searchTerm);
        model.addAttribute("bookshelfVolumes", bookshelfVolumes);
        model.addAttribute("requestingUsername", user.getUsername());
        session.setAttribute("requestingUsername", user.getUsername());

        return "search/results";
    }

    //Request parameters here are then set as session attributes and retrieved in the /user/email Get.
    @PostMapping("/redirectToRequestEmailForm")
    public ModelAndView redirectToRequestEmailForm(Model model, @RequestParam String recipient,
                                                   @RequestParam String bookTitle, @RequestParam Integer bookId,
                                                   @RequestParam String requestingUsername, HttpSession session) throws IOException{
        session.setAttribute("recipient", recipient);
        session.setAttribute("bookTitle", bookTitle);
        session.setAttribute("bookId", bookId);
        session.setAttribute("requestingUsername", requestingUsername);

        return new ModelAndView("redirect:/user/email");
    }


}
