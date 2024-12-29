package org.launchcode.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Bookshelf extends AbstractEntity{

    private String bookshelf_name;
    private int bookshelf_user_id;

    public Bookshelf(String bookshelf_name, int bookshelf_user_id) {
        this.bookshelf_name = bookshelf_name;
        this.bookshelf_user_id = bookshelf_user_id;
    }

    public Bookshelf(){}

    public String getBookshelf_name() {
        return bookshelf_name;
    }

    public void setBookshelf_name(String bookshelf_name) {
        this.bookshelf_name = bookshelf_name;
    }

    public int getBookshelf_user_id() {
        return bookshelf_user_id;
    }

    public void setBookshelf_user_id(int bookshelf_user_id) {
        this.bookshelf_user_id = bookshelf_user_id;
    }

    @Override
    public String toString() { return bookshelf_name; }
}
