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
    public Float rating_avg;

    public Volume(String id, String author, String title, String description, String thumbnail, Float rating_avg) {

        this.id = id;
        this.author = author;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.rating_avg = rating_avg;
    }

    public Volume(){}


    @Override
    public String toString() {
        return "Volume{" +
                "Google ID='" + id + '\'' +
                ", Author(s)='" + author + '\'' +
                ", Title='" + title + '\'' +
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

    public Float getRating_avg() {
        return rating_avg;
    }
    public void setRating_avg(Float rating_avg) {
        this.rating_avg = rating_avg;
    }
}
