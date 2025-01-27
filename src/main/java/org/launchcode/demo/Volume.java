//package org.launchcode.demo;
//
//
//
//
//public class Volume {
//    // initialize fields
//    private String id;
//    private String author;
//    private String title;
//    private String description;
//    private String thumbnail;
//
//    //constructor
//
//    public Volume(String id, String author, String title, String description, String thumbnail) {
//        this.id = id;
//        this.author = author;
//        this.title = title;
//        this.description = description;
//        this.thumbnail = thumbnail;
//    }
//    //default constructor
//    public Volume(){
//        this("No ID", "Author Unknown", "Title", "No Description Given", "No Thumbnail Available.");
//    }
//
//    //Method overrides
//    @Override
//    public String toString() {
//        return "Volume{" +
//                "Google ID='" + id + '\'' +
//                ", Title='" + author + '\'' +
//                ", Author(s)='" + title + '\'' +
//                ", Description='" + description + '\'' +
//                ", Thumbnail='" + thumbnail;
//
//    }
//
//    // getters and setters
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getAuthor() {
//        return author;
//    }
//
//    public void setAuthor(String author) {
//        this.author = author;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public String getThumbnail() {
//        return thumbnail;
//    }
//
//    public void setThumbnail(String thumbnail) {
//        this.thumbnail = thumbnail;
//    }
//}
