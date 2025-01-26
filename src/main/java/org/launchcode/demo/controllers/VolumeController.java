package org.launchcode.demo.controllers;

import org.launchcode.demo.data.VolumeRepository;
import org.launchcode.demo.models.Volume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("volumes")
public class VolumeController{

    @Autowired
    public VolumeRepository volumeRepository;

    @GetMapping
    public String displayAllVolumes(Model model){
        model.addAttribute("title", "All Books");
        model.addAttribute("volumes", volumeRepository.findAll());
        return "volumes/index";
    }



}
