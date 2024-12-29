package org.launchcode.demo.controllers;

import org.launchcode.demo.data.BookshelfVolumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("library")
public class BookshelfVolumeController {

    @Autowired
    public BookshelfVolumeRepository bookshelfVolumeRepository;

    @GetMapping
    public String displayAllVolumes(Model model){
            model.addAttribute("title", "All Books");
            model.addAttribute("bookshelf_volumes", bookshelfVolumeRepository.findAll());
        return "library/index";
    }

    //TODO add BookVolumeControllerDTO and add handler here
}
