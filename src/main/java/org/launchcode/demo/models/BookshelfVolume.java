package org.launchcode.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import java.util.ArrayList;
import java.util.List;

@Entity
public class BookshelfVolume extends AbstractEntity{

    @ManyToOne
    private Bookshelf bookshelf;

    @ManyToOne
    private Volume volume;

    private Boolean has_book = true;
    private Float rating;
    private String swapHistory = "";

    @ManyToMany
    private List<Tag> tags = new ArrayList<>();

    public BookshelfVolume(Bookshelf bookshelf, Volume volume, Boolean has_book, String swap_history) {
        this.bookshelf = bookshelf;
        this.volume = volume;
        this.has_book = has_book;
        this.swapHistory = swap_history;
    }


    public BookshelfVolume(){}

    public Bookshelf getBookshelf() {
        return bookshelf;
    }

    public void setBookshelf(Bookshelf bookshelf) {
        this.bookshelf = bookshelf;
    }

    public Volume getVolume() {
        return volume;
    }

    public void setVolume(Volume volume) {
        this.volume = volume;
    }

    public Boolean getHas_book() {
        return has_book;
    }

    public void setHas_book(Boolean has_book) {
        this.has_book = has_book;
    }

    public Float getRating() {return rating;}

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void addTag(Tag tag){
        this.tags.add(tag);
    }

    public String getSwap_history() {
        return swapHistory;
    }

    public void setSwap_history(String swap_history) {
        this.swapHistory = swap_history;
    }

    public void updateSwapHistory(String username){
        this.swapHistory = this.swapHistory + ", " + username;
    }
}
