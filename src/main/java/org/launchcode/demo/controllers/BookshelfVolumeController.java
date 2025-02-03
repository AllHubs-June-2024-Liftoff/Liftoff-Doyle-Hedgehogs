package org.launchcode.demo.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.launchcode.demo.data.*;
import org.launchcode.demo.models.*;
import org.launchcode.demo.models.dto.BookshelfVolumeTagDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("book")
public class BookshelfVolumeController {

    @Autowired
    public BookshelfVolumeRepository bookshelfVolumeRepository;

    @Autowired
    public BookshelfRepository bookshelfRepository;

    @Autowired
    public VolumeRepository volumeRepository;

    @Autowired
    public TagRepository tagRepository;

    @Autowired
    public UserRepository userRepository;

    //Loads view of Google Books API search when user clicks "Add a Book" from their library//
    @GetMapping("addBook")
    public String search(Model model, HttpSession session) {
        Integer bookshelfId = (Integer) session.getAttribute("bookshelfId");
        session.setAttribute("bookshelfId", bookshelfId);
        model.addAttribute("bookshelfId", bookshelfId);
        model.addAttribute("title", "Add a New Book to the Library");
        return "book/addBook";
    }

    @PostMapping("/rate")
    public String rateBook(@RequestParam int id, @RequestParam(required = false) Float rating) {
        BookshelfVolume bookshelfVolume = bookshelfVolumeRepository.findById(id).get();
        bookshelfVolume.setRating(rating);
        bookshelfVolumeRepository.save(bookshelfVolume);
        return "redirect:/bookshelf/view/" + bookshelfVolume.getBookshelf().getUser().getUsername();
    }

    //Hidden redirect (no user view) transferring search results and bookshelfId to results page//
    @PostMapping("api/results")
    public String handleAPISearchPostRequest(@RequestParam String searchTerm,
                                             HttpSession session) throws IOException {
        if (searchTerm.isEmpty()){
            return "redirect:../addBook";
        }
        String rawResults = ApiActions.ApiSearch(searchTerm);
        Object parsedResults = new ArrayList<Volume>(ApiActions.ParseResults(rawResults));
        Integer bookshelfId = (Integer) session.getAttribute("bookshelfId");
        session.setAttribute("bookshelfId", bookshelfId);
        session.setAttribute("parsedResults", parsedResults);

        return "redirect:../addBook/results";
    }

    //User view of API search results//
    @GetMapping("/addBook/results")
    public String displaySearchResults(Model model, HttpSession session) {

        model.addAttribute("title", "Add a New Book to the Library");
        Object parsedResults = session.getAttribute("parsedResults");
        Integer bookshelfId = (Integer) session.getAttribute("bookshelfId");
        model.addAttribute("searchResults", parsedResults);
        model.addAttribute("bookshelfId", bookshelfId);
        return "book/addBook/results";
    }

    //Hidden redirect sending selected result's data to "add book" customization form//
    @PostMapping("/api/results/selected")
    public ModelAndView passVolumeDataFromSearchResult(String author,
                                                       String bookId,
                                                       String bookTitle, String description, String thumbnail,
                                                       HttpSession session) throws IOException {

        Integer bookshelfId = (Integer) session.getAttribute("bookshelfId");
        session.setAttribute("bookId", bookId);
        session.setAttribute("bookTitle", bookTitle);
        session.setAttribute("author", author);
        session.setAttribute("description", description);
        session.setAttribute("thumbnail", thumbnail);
        session.setAttribute("bookshelfId", bookshelfId);

        return new ModelAndView("redirect:/book/addToBookshelf");
    }

    //Displays book data and checkboxes for adding tags; when submitted, creates a new Volume
    // object if none with this Google Books API id exists in the database
    @GetMapping("addToBookshelf")
    public String displayNewBookshelfVolumeForm(HttpSession session, Model model) {
        Object bookId = session.getAttribute("bookId");
        Object bookTitle = session.getAttribute("bookTitle");
        Object author = session.getAttribute("author");
        String description = (String) session.getAttribute("description");
        Object thumbnail = session.getAttribute("thumbnail");
        Integer bookshelfId = (Integer) session.getAttribute("bookshelfId");

        if (volumeRepository.findById(bookId.toString()).isEmpty()){
            Volume newVolume = new Volume(bookId.toString(), author.toString(), bookTitle.toString(),
                    description, thumbnail.toString(), null);
            volumeRepository.save(newVolume);
            model.addAttribute("volume", newVolume);
        } else {
            model.addAttribute("volume", volumeRepository.findById(bookId.toString()));
        }
        Iterable<Tag> allTags = tagRepository.findAllByOrderByNameAsc();
        BookshelfVolumeTagDTO bookshelfVolumeTag = new BookshelfVolumeTagDTO();

        model.addAttribute("bookshelfId", bookshelfId);
        model.addAttribute("title", "Add to My Bookshelf");
        model.addAttribute("bookId", bookId);
        model.addAttribute("bookTitle", bookTitle);
        model.addAttribute("author", author);
        model.addAttribute("description", description);
        model.addAttribute("thumbnail", thumbnail);
        model.addAttribute("allTags", allTags);
        model.addAttribute("bookshelfVolumeTag", bookshelfVolumeTag);

        return "book/addToBookshelf";
    }

    //Processes addBook form, adds book to user's bookshelf, and redirects back to bookshelf
    @PostMapping("addToBookshelf")
    public ModelAndView processNewBookshelfVolumeForm(Model model, HttpSession session,
                                                BookshelfVolumeTagDTO bookshelfVolumeTag) throws IOException {
        String bookId = (String) session.getAttribute("bookId");
        BookshelfVolume bookshelfVolume = new BookshelfVolume();
        bookshelfVolumeTag.setBookshelfVolume(bookshelfVolume);
        Integer bookshelfId = (Integer) session.getAttribute("bookshelfId");
        Optional<Bookshelf> bookshelf = bookshelfRepository.findById(bookshelfId);
        Bookshelf theBookshelf = bookshelf.get();
        bookshelfVolume.setBookshelf(theBookshelf);
        Optional<Volume> optionalVolume = volumeRepository.findById(bookId);
        bookshelfVolume.setVolume(optionalVolume.get());
        bookshelfVolume.setHas_book(true);
        bookshelfVolume.setTags(bookshelfVolumeTag.getTags());
        bookshelfVolume.setSwapHistory(null);
        bookshelfVolume.setPendingTransferTo(null);
        bookshelfVolumeRepository.save(bookshelfVolume);
        String username = theBookshelf.getUser().getUsername();

        return new ModelAndView("redirect:/bookshelf/view/" + username);
    }

    @PostMapping("add")
    public String processNewBookshelfVolumeForm(@ModelAttribute @Valid BookshelfVolumeTagDTO bookshelfVolumeTag,
                                                Model model, Errors errors){
        if (!errors.hasErrors()) {
            BookshelfVolume bookshelfVolume = bookshelfVolumeTag.getBookshelfVolume();
            List<Tag> tags = bookshelfVolumeTag.getTags();
            bookshelfVolume.setTags(tags);
            bookshelfVolumeRepository.save(bookshelfVolume);

            return "book/add";
        }

        return "redirect:";
    }

    @GetMapping("update-or-remove/{id}")
    public String displayUpdateOrRemoveBookForm(@PathVariable Integer id, Model model){
        Optional<BookshelfVolume> optionalBookshelfVolume = bookshelfVolumeRepository.findById(id);
        BookshelfVolume bookshelfVolume = optionalBookshelfVolume.get();
        String currentHasBookStatus;
        String hasBookUpdateOption;
        if (bookshelfVolume.getHas_book()){
            currentHasBookStatus = "Visible to other users";
            hasBookUpdateOption = "Hidden (keep on my bookshelf but don't show in searches)";
        } else {
            currentHasBookStatus = "Hidden";
            hasBookUpdateOption = "Visible (show in searches)";
        }
        HashMap<Integer, String> updateOptions = new HashMap<>();
        if (bookshelfVolume.getPendingTransferTo() != null){
            updateOptions.put(0, "Transfer to requester");
        }
        updateOptions.put(1, "Change visibility (search status)");
        updateOptions.put(2, "Remove from Little Online Library entirely");

        model.addAttribute("title", "Update '" + bookshelfVolume.getVolume().getTitle()
                + "' or Remove from " + bookshelfVolume.getBookshelf().getBookshelf_name());
        model.addAttribute("bookshelfVolume", bookshelfVolume);
        model.addAttribute("id", id);
        model.addAttribute("updateOptions", updateOptions);
        model.addAttribute("hasBookUpdateOption", hasBookUpdateOption);
        model.addAttribute("currentHasBookStatus", currentHasBookStatus);

        return "book/update-or-remove";
    }

    @PostMapping("update-or-remove/{id}")
    public String processRemoveBookForm(@RequestParam(value="id") Integer id,
                                        @RequestParam(value="updateOption") Integer updateOption,
                                        Model model){
        Optional<BookshelfVolume> optBookshelfVolume = bookshelfVolumeRepository.findById(id);
        BookshelfVolume bookshelfVolume = optBookshelfVolume.get();
        String username = bookshelfVolume.getBookshelf().getUser().getUsername();
        User pendingTransferToUser = userRepository.findByUsername(bookshelfVolume.getPendingTransferTo());
        Bookshelf destinationBookshelf = bookshelfRepository.findByUser(pendingTransferToUser);

        if (updateOption == null){
            //Simply reloads the page if submitted when no selection is made//
            return "redirect:../remove-or-update/" + id;
        } else if (updateOption.equals(0)){
            //Transfer book to another user//
            bookshelfVolume.setHas_book(true);
            bookshelfVolume.setBookshelf(destinationBookshelf);
            bookshelfVolume.setPendingTransferTo(null);
            bookshelfVolume.updateSwapHistory(username);
            bookshelfVolumeRepository.save(bookshelfVolume);
        } else if (updateOption.equals(1)){
            if (bookshelfVolume.getHas_book()){
                bookshelfVolume.setHas_book(false);
            } else {
                bookshelfVolume.setHas_book(true);
                bookshelfVolume.setPendingTransferTo(null);
            }
            bookshelfVolumeRepository.save(bookshelfVolume);
        } else if (updateOption.equals(2)){
            //Delete book from the site altogether//
            bookshelfVolumeRepository.deleteById(id);
        }

        return "redirect:/bookshelf/view/" + username;
    }

}
