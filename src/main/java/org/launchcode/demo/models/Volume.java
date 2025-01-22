package org.launchcode.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import javax.swing.*;

@Entity
public class Volume{

    @Id
    private String id;

    private String authors;
    private String title;
    private String description;
    private String thumbnail;

    //Try this to construct new Volume object out of Api result object//
//    public Volume(Action action){
//        this.id = (String) action.getValue(Action.ID);
//        this.title = (String) action.getValue(Action.TITLE);
//        this.authors = (String) action.getValue(Action.AUTHORS);
//        this.description = (String) action.getValue(Action.DESCRIPTION);
//        this.thumbnail = (String) action.getValue(Action.THUMBNAIL);
//    }

    public Volume(String id, String authors, String title, String description, String thumbnail) {

        this.id = id;
        this.authors = authors;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public Volume(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String author) {
        this.authors = authors;
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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

}
