package org.launchcode.demo.controllers;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import org.launchcode.demo.data.BookshelfRepository;
import org.launchcode.demo.models.BookshelfVolume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("bookshelves")
public class BookshelfController {

    @Autowired
    public BookshelfRepository bookshelfRepository;


}
