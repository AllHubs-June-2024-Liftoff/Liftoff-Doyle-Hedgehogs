package org.launchcode.demo.controllers;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.websocket.server.PathParam;
import org.launchcode.demo.data.BookshelfRepository;
import org.launchcode.demo.models.Bookshelf;
import org.launchcode.demo.models.BookshelfVolume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("bookshelf")
public class BookshelfController {

    @Autowired
    public BookshelfRepository bookshelfRepository;

    @GetMapping("/{id}")
    public String displayBookshelfLibrary(@PathVariable Integer id, Model model){

        Optional<Bookshelf> result = bookshelfRepository.findById(id);
        Bookshelf bookshelf = result.get();
        model.addAttribute("title", "Bookshelf: " + bookshelf.getBookshelf_name());
        model.addAttribute("bookshelfVolumes", bookshelf.getBookshelfVolumes());

        return "bookshelf/view";
}
}
