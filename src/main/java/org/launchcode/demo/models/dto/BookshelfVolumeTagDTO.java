package org.launchcode.demo.models.dto;

import org.launchcode.demo.models.BookshelfVolume;
import org.launchcode.demo.models.Tag;

public class BookshelfVolumeTagDTO {

    public BookshelfVolume bookshelfVolume;

    public Tag tag;

    public BookshelfVolumeTagDTO(){}

    public BookshelfVolume getBookshelfVolume() {
        return bookshelfVolume;
    }

    public void setBookshelfVolume(BookshelfVolume bookshelfVolume) {
        this.bookshelfVolume = bookshelfVolume;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
