package org.launchcode.demo.controllers;

import org.launchcode.demo.data.BookshelfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("bookshelves")
public class BookshelfController {

    @Autowired
    public BookshelfRepository bookshelfRepository;


}
