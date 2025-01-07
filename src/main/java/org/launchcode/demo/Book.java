package org.launchcode.demo;


public class Book {
    // initialize fields
    private String title;
    private String author;


    //constructor
    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    // getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
