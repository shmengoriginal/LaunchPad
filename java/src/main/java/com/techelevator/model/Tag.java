package com.techelevator.model;

public class Tag {
    int id;
    String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Tag(){};

    public Tag(int id, String description) {
        this.id = id;
        this.description = description;
    }
}
