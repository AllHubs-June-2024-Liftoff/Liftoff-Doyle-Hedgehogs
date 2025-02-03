package org.launchcode.demo.models.dto;

import jakarta.validation.constraints.NotNull;
import org.launchcode.demo.models.BookshelfVolume;
import org.launchcode.demo.models.Tag;

import java.util.List;

public class BookshelfVolumeTagDTO {

    @NotNull
    private BookshelfVolume bookshelfVolume;

    @NotNull
    private List<Tag> tags;

    public BookshelfVolumeTagDTO(){}

    public BookshelfVolume getBookshelfVolume() {
        return bookshelfVolume;
    }

    public BookshelfVolume getBookshelfVolumeById(Integer id){ return bookshelfVolume;}

    public void setBookshelfVolume(BookshelfVolume bookshelfVolume) {
        this.bookshelfVolume = bookshelfVolume;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

}
