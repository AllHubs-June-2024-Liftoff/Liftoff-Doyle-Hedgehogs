package org.launchcode.demo.controllers;

import jakarta.validation.Valid;
import org.launchcode.demo.data.BookshelfRepository;
import org.launchcode.demo.data.BookshelfVolumeRepository;
import org.launchcode.demo.data.TagRepository;
import org.launchcode.demo.models.BookshelfVolume;
import org.launchcode.demo.models.Tag;
import org.launchcode.demo.models.dto.BookshelfVolumeTagDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

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

    //responds to requests at /book/addtags?id=[bookshelfvolumeid]
    // Add tags on an individual BookshelfVolume; should be an option when creating a new BSV and route back to the create page //
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
    public String processBAddTagForm(@ModelAttribute @Valid BookshelfVolumeTagDTO bookshelfVolumeTag,
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
