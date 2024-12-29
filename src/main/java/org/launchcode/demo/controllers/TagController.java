package org.launchcode.demo.controllers;

import org.launchcode.demo.data.TagRepository;
import org.launchcode.demo.models.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("tags")
public class TagController {

    @Autowired
    TagRepository tagRepository;

    @GetMapping
    public String displayAllTags(Model model){
        model.addAttribute("title", "Tags");
        model.addAttribute("tags", tagRepository.findAllByOrderByNameAsc());
        return "tags/index";
    }

    @GetMapping("create")
    public String displayCreateTagForm(Model model){
        model.addAttribute("title", "Add New Tag");
        model.addAttribute(new Tag());
        return "tags/create";
    }

    //TODO complete create tag form view and processing method
//    public String processCreateTagForm(Model model){
//
//    }
}
