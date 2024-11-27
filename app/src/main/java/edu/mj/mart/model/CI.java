package edu.mj.mart.model;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class CI implements Serializable {

    private String name;

    @Exclude
    private String id;

    @Exclude
    private boolean isSelect;

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

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
