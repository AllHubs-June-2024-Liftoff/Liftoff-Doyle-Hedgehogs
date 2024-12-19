package org.launchcode.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class Volume extends AbstractEntity{

    private String author;
    private String title;
    private String description;


    public Volume(String author, String title, String description) {
        this.title = title;
        this.author = author;
        this.description = description;
    }

    public Volume(){}

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
