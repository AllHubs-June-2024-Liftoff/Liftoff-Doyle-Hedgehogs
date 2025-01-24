package org.launchcode.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Bookshelf extends AbstractEntity{

    private String bookshelf_name;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "bookshelf")
    private final List<BookshelfVolume> bookshelfVolumes = new ArrayList<>();

    public Bookshelf(String bookshelf_name, User user) {
        this.bookshelf_name = bookshelf_name;
        this.user = user;
    }

    public Bookshelf(){}

    public String getBookshelf_name() {
        return bookshelf_name;
    }

    public void setBookshelf_name(String bookshelf_name) {
        this.bookshelf_name = bookshelf_name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<BookshelfVolume> getBookshelfVolumes() {
        return bookshelfVolumes;
    }

    @Override
    public String toString() { return bookshelf_name; }
}
