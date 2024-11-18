package edu.mj.mart.model;

import java.io.Serializable;

public class CI implements Serializable {

    private String name;
    private String id;

    public CI(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public CI() {
    }

    public CI(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
