package edu.mj.mart.model;

import static edu.mj.mart.utils.Constants.ACCOUNT_ACTIVE;
import static edu.mj.mart.utils.Constants.ACCOUNT_AVATAR;
import static edu.mj.mart.utils.Constants.ACCOUNT_EMAIL;
import static edu.mj.mart.utils.Constants.ACCOUNT_FULL_NAME;
import static edu.mj.mart.utils.Constants.ACCOUNT_PASSWORD;
import static edu.mj.mart.utils.Constants.ACCOUNT_PHONE;
import static edu.mj.mart.utils.Constants.ACCOUNT_ROLE;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class Account implements Serializable {

    private String id;
    private int role;
    private String email;
    private String password;
    private String phone;
    private String fullName;
    private int active;
    private List<String> avatar;

    public Account(String id, int role, String email, String password, String phone, String fullName, int active, List<String> avatar) {
        this.id = id;
        this.role = role;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.fullName = fullName;
        this.active = active;
        this.avatar = avatar;
    }

    public Account(String id, int role, String email, String phone, String fullName, int active, List<String> avatar) {
        this.id = id;
        this.role = role;
        this.email = email;
        this.phone = phone;
        this.fullName = fullName;
        this.active = active;
        this.avatar = avatar;
    }

    public Account() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public List<String> getAvatar() {
        return avatar;
    }

    public void setAvatar(List<String> avatar) {
        this.avatar = avatar;
    }

    public HashMap<String, Object> convertToHashMap() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(ACCOUNT_ROLE, role);
        hashMap.put(ACCOUNT_EMAIL, email);
        hashMap.put(ACCOUNT_PASSWORD, password);
        hashMap.put(ACCOUNT_PHONE, phone);
        hashMap.put(ACCOUNT_FULL_NAME, fullName);
        hashMap.put(ACCOUNT_ACTIVE, active);
        hashMap.put(ACCOUNT_AVATAR, avatar);

        return hashMap;
    }
}
