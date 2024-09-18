package org.example.hotel.models;

import org.example.hotel.enums.Role;
import org.example.hotel.utils.DatabaseUtil;

import java.util.Date;

public class User extends Person {
    private Role role;
    private String position;
    private Double salary;
    private String password;

    public User(Hotel hotel,
                String firstName,
                String lastName,
                String email,
                String phoneNum,
                String passportNum,
                Role role,
                String position,
                Double salary,
                String password,
                Date createdAt,
                Date updatedAt) {
        super(hotel, firstName, lastName, email, phoneNum, passportNum, createdAt, updatedAt);
        this.role = role;
        this.position = position;
        this.salary = salary;
        this.password = password;
    }

    public User(User other) {
        super(other);
        this.role = other.role;
        this.position = other.position;
        this.salary = other.salary;
        this.password = other.password;
    }

    public Role getRole() {
        return this.role;
    }

    public String getPosition() {
        return this.position;
    }

    public Double getSalary() {
        return this.salary;
    }

    public String getPassword() {
        return this.password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public void setPassword(String password) {
        this.password = DatabaseUtil.hashPassword(password);
    }
}
