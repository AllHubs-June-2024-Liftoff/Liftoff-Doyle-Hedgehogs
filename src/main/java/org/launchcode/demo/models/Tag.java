package org.launchcode.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Tag extends AbstractEntity{

    @Size(min = 3, max = 25)
    @NotBlank
    public String name;

    @ManyToMany(mappedBy = "tags")
    private final List<BookshelfVolume> bookshelfVolumes = new ArrayList<>();

    public Tag(String name) {
        this.name = name;
    }

    public Tag(){}

    public String getName() {
        return name;
    }

    public String getDisplayName(){
        return "#" + name + " ";
    }

    public void setName(@Size(min = 3, max = 25) @NotBlank String name) {
        this.name = name;
    }

    public List<BookshelfVolume> getBookshelfVolumes() {
        return bookshelfVolumes;
    }
}
