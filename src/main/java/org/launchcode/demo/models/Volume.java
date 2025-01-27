package org.launchcode.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Volume{

    @Id
    private String id;

    private String author;
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

    public Volume(String id, String author, String title, String description, String thumbnail) {

        this.id = id;
        this.author = author;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public Volume(){}


    @Override
    public String toString() {
        return "Volume{" +
                "Google ID='" + id + '\'' +
                ", Title='" + author + '\'' +
                ", Author(s)='" + title + '\'' +
                ", Description='" + description + '\'' +
                ", Thumbnail='" + thumbnail;

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = this.author;
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
