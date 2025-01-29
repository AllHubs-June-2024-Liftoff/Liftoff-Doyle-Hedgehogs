package org.launchcode.demo.controllers;

import jakarta.servlet.http.HttpSession;
import org.launchcode.demo.data.VolumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("volume")
public class VolumeController{

    @Autowired
    public VolumeRepository volumeRepository;

    @GetMapping
    public String displayAllVolumes(Model model){
        model.addAttribute("title", "All Books");
        model.addAttribute("volumes", volumeRepository.findAll());
        return "volumes/index";
    }

//    TODO: add method to check Volume table and create new Volume object from selected API search result
@GetMapping("add")
public String displayNewBookshelfVolumeForm(HttpSession session, Model model) {
    Object author = session.getAttribute("author");
    model.addAttribute("author", author);

    return "volume/add";
}

}
