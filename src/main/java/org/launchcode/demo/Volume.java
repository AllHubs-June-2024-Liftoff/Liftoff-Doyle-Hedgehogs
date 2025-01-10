package org.launchcode.demo;


import java.net.URL;

public class Volume {
    // initialize fields
    private String id;
    private String authors;
    private String title;
    private String description;
    private URL thumbnail;

    //constructor
    public Volume(String id, String authors,String title, String description) {
        this.id = id;
        this.authors = authors;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
    }
    //default constructor
    public Volume(){
        this("No ID", "Author Unknown", "Title", "No Description Given");
    }

    //Method overrides
    @Override
    public String toString() {
        return "Book{" +
                "Google ID='" + id + '\'' +
                ", Title='" + authors + '\'' +
                ", Author(s)='" + title + '\'' +
                ", Description='" + description + '\'' +
                ", Thumbnail='" + thumbnail;

    }

    // getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public URL getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(URL thumbnail) {
        this.thumbnail = thumbnail;
    }
}
