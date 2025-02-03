package org.launchcode.demo.controllers;

import jakarta.servlet.http.HttpSession;
import org.launchcode.demo.data.TagRepository;
import org.launchcode.demo.data.VolumeRepository;
import org.launchcode.demo.models.Volume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("volume")
public class VolumeController{

    @Autowired
    public VolumeRepository volumeRepository;

    @Autowired
    public TagRepository tagRepository;

    @GetMapping
    public String displayAllVolumes(Model model){
        model.addAttribute("title", "All Books");
        model.addAttribute("volumes", volumeRepository.findAll());
        return "volumes/index";
    }

//    TODO: add method to check Volume table and create new Volume object from selected API search result
    @GetMapping("add")
    public String displayNewBookshelfVolumeForm(HttpSession session, Model model) {
        Object bookId = session.getAttribute("bookId");
        Object bookTitle = session.getAttribute("bookTitle");
        Object author = session.getAttribute("author");
        Object description = session.getAttribute("description");
        Object thumbnail = session.getAttribute("thumbnail");
        if (volumeRepository.findById(bookId.toString()).isEmpty()){
            Volume newVolume = new Volume(bookId.toString(), bookTitle.toString(), author.toString(),
                    description.toString(), thumbnail.toString());
            volumeRepository.save(newVolume);
        }
        model.addAttribute("title", "Add to My Bookshelf");
        model.addAttribute("bookId", bookId);
        model.addAttribute("bookTitle", bookTitle);
        model.addAttribute("author", author);
        model.addAttribute("description", description);
        model.addAttribute("thumbnail", thumbnail);
        model.addAttribute("allTags", tagRepository.findAll());

    return "volume/add";
    }

}
