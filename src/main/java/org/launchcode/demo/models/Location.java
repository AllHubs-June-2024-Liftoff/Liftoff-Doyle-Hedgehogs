package org.launchcode.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Location extends AbstractEntity{

    public String name;

    @OneToMany(mappedBy = "location")
    public final List<User> users = new ArrayList<>();

    public Location(String name) {
        this();
        this.name = name;
    }

    public Location(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
