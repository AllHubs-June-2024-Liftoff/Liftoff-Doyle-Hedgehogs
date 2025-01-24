package org.launchcode.demo.controllers;

import jakarta.validation.Valid;
import org.launchcode.demo.data.TagRepository;
import org.launchcode.demo.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

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

    @PostMapping("create")
    public String processCreateTagForm(@ModelAttribute @Valid Tag tag, Errors errors, Model model){

        formatTagName(tag);

        if (errors.hasErrors()) {
            model.addAttribute("title", "Create Tag");
            model.addAttribute(tag);
            return "tags/create";
        }

        tagRepository.save(tag);
        return "redirect:/tags";
    }

    @GetMapping("/{tagId}")
    public String displayByTag(Model model, @PathVariable int tagId){
        Optional<Tag> optionalTag = tagRepository.findById(tagId);
        if (optionalTag.isPresent()){
            Tag tag = (Tag) optionalTag.get();
            model.addAttribute("tag", tag);
            return "tags/view";
        } else {
            return "redirect:../";
        }
    }

    // Formats tag name to all lowercase and removes non-alphanumeric characters
    public String formatTagName(Tag tag){
        String name = tag.getName().toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
        tag.setName(name);
        return name;
    }

}