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
    public Volume volume;
    private Boolean has_book = true;

    @ManyToMany
    public List<Tag> tags = new ArrayList<>();

    public BookshelfVolume(Bookshelf bookshelf, Volume volume, Boolean has_book) {
        this.bookshelf = bookshelf;
        this.volume = volume;
        this.has_book = has_book;

    }

    //TODO add swapHistory as ArrayList? User can optionally append their username when accepting a book (popup dialog box)

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

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
