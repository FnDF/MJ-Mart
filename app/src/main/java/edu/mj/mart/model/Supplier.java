package edu.mj.mart.model;

import java.io.Serializable;

public class Supplier implements Serializable {

    private String id;
    private String name;
    private String phone;

    public Supplier(String id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public Supplier() {
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}
