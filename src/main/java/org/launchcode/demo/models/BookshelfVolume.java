package org.launchcode.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class BookshelfVolume extends AbstractEntity{

    @ManyToOne
    private Bookshelf bookshelf;

    @ManyToOne
    public Volume volume;
    private Boolean has_book = true;

    public BookshelfVolume(Bookshelf bookshelf, Volume volume, Boolean has_book) {
        this.bookshelf = bookshelf;
        this.volume = volume;
        this.has_book = has_book;
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
}
