package edu.mj.mart.model;

import java.util.List;

public class Employee extends Account {

    public Employee(String id, int role, String email, String password, String phone, String fullName, int active, List<String> avatar) {
        super(id, role, email, password, phone, fullName, active, avatar);
    }

    public Employee(String id, int role, String email, String phone, String fullName, int active, List<String> avatar) {
        super(id, role, email, phone, fullName, active, avatar);
    }

    public Employee(int role, String email, String password, String phone, String fullName, List<String> avatar) {
        super(role, email, password, phone, fullName, avatar);
    }

    public Employee(String email, String password, String phone, String fullName, List<String> avatar) {
        super(email, password, phone, fullName, avatar);
    }

    public Employee() {
    }
}
