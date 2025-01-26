package org.launchcode.demo.controllers;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.launchcode.demo.data.BookshelfRepository;
import org.launchcode.demo.data.BookshelfVolumeRepository;
import org.launchcode.demo.data.TagRepository;
import org.launchcode.demo.models.*;
import org.launchcode.demo.models.dto.BookshelfVolumeTagDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
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
    public TagRepository tagRepository;

    //should be routed TO by user selecting a row from API results to add to their library//
    @GetMapping("add")
    public String displayNewBookshelfVolumeForm(Volume volume, Bookshelf bookshelf, Model model) {
        BookshelfVolume bookshelfVolume = new BookshelfVolume();
        bookshelfVolume.setBookshelf(bookshelf);
        bookshelfVolume.setVolume(volume);
        Iterable<Tag> allTags = tagRepository.findAllByOrderByNameAsc();

        BookshelfVolumeTagDTO bookshelfVolumeTag = new BookshelfVolumeTagDTO();
        bookshelfVolumeTag.setBookshelfVolume(bookshelfVolume);

        model.addAttribute("title", "Add a Book");
        model.addAttribute("bookshelfVolume", bookshelfVolume);
        model.addAttribute("volume", volume);
        model.addAttribute("bookshelfVolumeTag", bookshelfVolumeTag);
        model.addAttribute("allTags", allTags);

        return "book/add";
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

    @GetMapping("remove/{id}")
    public String displayRemoveBookForm(@PathVariable Integer id, Model model){
        Optional<BookshelfVolume> optionalBookshelfVolume = bookshelfVolumeRepository.findById(id);
        BookshelfVolume bookshelfVolume = optionalBookshelfVolume.get();
        ArrayList<Integer> removeTypes = new ArrayList<>();
//                List.of("transfer", "hide", "delete"));
        removeTypes.add(0);
        removeTypes.add(1);
        removeTypes.add(2);


        model.addAttribute("title", "Remove " + bookshelfVolume.getVolume().getTitle()
                + " from " + bookshelfVolume.getBookshelf().getBookshelf_name());
        model.addAttribute("bookshelfVolume", bookshelfVolume);
        model.addAttribute("id", id);
        model.addAttribute("removeTypes", removeTypes);

        return "book/remove";
    }

    //Todo: complete this function and accompanying view; deletion works, but removeTypes 0 and 1 only update
    // values in IntelliJ, not in the MySQL database.//
    @PostMapping("remove/{id}")
    public String processRemoveBookForm(@RequestParam(value="id") Integer id, @RequestParam(required = false, value="removeTypes") Integer removeTypes){
        Optional<BookshelfVolume> optBookshelfVolume = bookshelfVolumeRepository.findById(id);
        BookshelfVolume bookshelfVolume = optBookshelfVolume.get();
        String username = bookshelfVolume.getBookshelf().getUser().getUsername();

//        TODO: uncomment when destination bookshelf info is connected
//        Optional<Bookshelf> optionalDestinationBookshelf = bookshelfRepository.findById(3);
//        Bookshelf destinationBookshelf = optionalDestinationBookshelf.get();
        if (removeTypes == null){
            //Simply reloads the page if submitted when no selection is made//
            return "redirect:/book/remove/" + id;
        } else if (removeTypes.equals(0)){
            //Transfer book to another user//
            bookshelfVolume.setHas_book(false);
//            bookshelfVolume.setBookshelf(destinationBookshelf);
//            bookshelfVolume.updateSwapHistory("");
            bookshelfVolumeRepository.save(bookshelfVolume);
        } else if (removeTypes.equals(1)){
            //Hide book from searches but keep on bookshelf//
            bookshelfVolume.setHas_book(false);
            bookshelfVolumeRepository.save(bookshelfVolume);
        } else if (removeTypes.equals(2)){
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
        model.addAttribute("title", "Tags for " + bookshelfVolume.getVolume().getTitle() + " by " + bookshelfVolume.getVolume().getAuthors());
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

//    @GetMapping("remove")
//    public String removeFromBookshelf(Model model){
//
//        return "book/remove";
//    }

}
