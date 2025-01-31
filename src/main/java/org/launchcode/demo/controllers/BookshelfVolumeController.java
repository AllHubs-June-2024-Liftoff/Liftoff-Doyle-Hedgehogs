package org.launchcode.demo.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.launchcode.demo.data.BookshelfRepository;
import org.launchcode.demo.data.BookshelfVolumeRepository;
import org.launchcode.demo.data.TagRepository;
import org.launchcode.demo.data.VolumeRepository;
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

    //should be routed TO by user selecting a row from API results to add to their library//
//    @GetMapping("add")
//    public String displayNewBookshelfVolumeForm(@RequestParam Bookshelf theBookshelf, Model model) {
//        BookshelfVolume bookshelfVolume = new BookshelfVolume();
//        bookshelfVolume.setBookshelf(theBookshelf);
//        bookshelfVolume.setVolume(volume);
//        Iterable<Tag> allTags = tagRepository.findAllByOrderByNameAsc();
//
//        BookshelfVolumeTagDTO bookshelfVolumeTag = new BookshelfVolumeTagDTO();
//        bookshelfVolumeTag.setBookshelfVolume(bookshelfVolume);
//
//        model.addAttribute("title", "Add a Book");
//        model.addAttribute("bookshelfVolume", bookshelfVolume);
//        model.addAttribute("volume", volume);
//        model.addAttribute("bookshelfVolumeTag", bookshelfVolumeTag);
//        model.addAttribute("allTags", allTags);
//
//        return "book/add";
//    }

    @GetMapping("addToBookshelf")
    public String displayNewBookshelfVolumeForm(HttpSession session, Model model) {
        Object bookId = session.getAttribute("bookId");
        Object bookTitle = session.getAttribute("bookTitle");
        Object author = session.getAttribute("author");
        Object description = session.getAttribute("description");
        Object thumbnail = session.getAttribute("thumbnail");
        Boolean has_book = (Boolean) session.getAttribute("has_book");
//        session.setAttribute("has_Book", has_Book);
        Integer bookshelfId = (Integer) session.getAttribute("bookshelfId");
        if (volumeRepository.findById(bookId.toString()).isEmpty()){
            Volume newVolume = new Volume(bookId.toString(), author.toString(), bookTitle.toString(),
                    description.toString(), thumbnail.toString());
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
        model.addAttribute("has_book", has_book);

        return "book/addToBookshelf";
    }

    @PostMapping("addToBookshelf")
    public ModelAndView processNewBookshelfVolumeForm(Model model, HttpSession session,
                                                BookshelfVolumeTagDTO bookshelfVolumeTag) throws IOException {
//        Object title = session.getAttribute("title");
        String bookId = (String) session.getAttribute("bookId");
        Boolean has_book = (Boolean) session.getAttribute("has_book");
        BookshelfVolume bookshelfVolume = new BookshelfVolume();
        bookshelfVolumeTag.setBookshelfVolume(bookshelfVolume);
        Integer bookshelfId = (Integer) session.getAttribute("bookshelfId");
        Optional<Bookshelf> bookshelf = bookshelfRepository.findById(bookshelfId);
        Bookshelf theBookshelf = bookshelf.get();
        bookshelfVolume.setBookshelf(theBookshelf);
        Optional<Volume> optionalVolume = volumeRepository.findById(bookId);
        bookshelfVolume.setVolume(optionalVolume.get());
        bookshelfVolume.setHas_book(has_book);
        bookshelfVolume.setTags(bookshelfVolumeTag.getTags());
        bookshelfVolume.setSwapHistory("");
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

    static HashMap<Integer, String> updateOptions = new HashMap<>();
    public BookshelfVolumeController(){
        updateOptions.put(0, "Transfer to another user");
        updateOptions.put(1, "Change visibility (search status)");
        updateOptions.put(2, "Remove from Little Online Library entirely");
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

        model.addAttribute("title", "Update " + bookshelfVolume.getVolume().getTitle()
                + " or Remove from " + bookshelfVolume.getBookshelf().getBookshelf_name());
        model.addAttribute("bookshelfVolume", bookshelfVolume);
        model.addAttribute("id", id);
        model.addAttribute("updateOptions", updateOptions);
        model.addAttribute("hasBookUpdateOption", hasBookUpdateOption);
        model.addAttribute("currentHasBookStatus", currentHasBookStatus);

        return "book/update-or-remove";
    }

    @PostMapping("update-or-remove/{id}")
    public String processRemoveBookForm(@RequestParam(value="id") Integer id, @RequestParam(required = false, value="updateOption") Integer updateOption){
        Optional<BookshelfVolume> optBookshelfVolume = bookshelfVolumeRepository.findById(id);
        BookshelfVolume bookshelfVolume = optBookshelfVolume.get();
        String username = bookshelfVolume.getBookshelf().getUser().getUsername();

//        TODO: uncomment when destination bookshelf info is connected
//        Optional<Bookshelf> optionalDestinationBookshelf = bookshelfRepository.findById(3);
//        Bookshelf destinationBookshelf = optionalDestinationBookshelf.get();
        if (updateOption == null){
            //Simply reloads the page if submitted when no selection is made//
            return "redirect:/book/remove-or-update/" + id;
        } else if (updateOption.equals(0)){
            //Transfer book to another user//
            bookshelfVolume.setHas_book(false);
//            bookshelfVolume.setBookshelf(destinationBookshelf);
//            bookshelfVolume.updateSwapHistory("");
            bookshelfVolumeRepository.save(bookshelfVolume);
        } else if (updateOption.equals(1)){
            if (bookshelfVolume.getHas_book()){
                bookshelfVolume.setHas_book(false);
            } else {
                bookshelfVolume.setHas_book(true);
            }
            bookshelfVolumeRepository.save(bookshelfVolume);
        } else if (updateOption.equals(2)){
            //Delete book from the site altogether//
            bookshelfVolumeRepository.deleteById(id);
        }

        return "redirect:/bookshelf/view/" + username;
    }


        //Remove all below this line once book/add is up and running//


    //responds to requests at /book/addtags?id=[bookshelfvolumeid]
    // Add tags on an individual BookshelfVolume; will be replaced by a portion of the book/add form //
    @GetMapping("addtags")
    public String displayAddTagForm(@RequestParam Integer id, Model model){
        Optional<BookshelfVolume> optionalBookshelfVolume = bookshelfVolumeRepository.findById(id);
        BookshelfVolume bookshelfVolume = optionalBookshelfVolume.get();
        Iterable<Tag> allTags = tagRepository.findAllByOrderByNameAsc();

        BookshelfVolumeTagDTO bookshelfVolumeTag = new BookshelfVolumeTagDTO();
        bookshelfVolumeTag.setBookshelfVolume(bookshelfVolume);

        model.addAttribute("username", bookshelfVolume.getBookshelf().getUser().getUsername());
        model.addAttribute("bookshelfVolumeTag", bookshelfVolumeTag);
        model.addAttribute("title", "Tags for " + bookshelfVolume.getVolume().getTitle() + " by " + bookshelfVolume.getVolume().getAuthor());
        model.addAttribute("bookshelfVolume", bookshelfVolume);
        model.addAttribute("allTags", allTags);
        model.addAttribute("bookshelfVolumeId", id);

        return "book/addtags";
    }

    @PostMapping("addtags")
    public String processAddTagForm(@ModelAttribute @Valid BookshelfVolumeTagDTO bookshelfVolumeTag,
                                           Model model, Errors errors){
        if (!errors.hasErrors()) {
            BookshelfVolume bookshelfVolume = bookshelfVolumeTag.getBookshelfVolume();
            List<Tag> tags = bookshelfVolumeTag.getTags();
            bookshelfVolume.setTags(tags);
            bookshelfVolumeRepository.save(bookshelfVolume);
            return "addtags?id=" + bookshelfVolume.getId();
        }
        return "redirect:addtags";
    }


}
