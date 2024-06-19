package org.example.hotel.models;

import java.util.Date;

public abstract class Person extends Entity {
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String phoneNum;
    protected String passportNum;

    public Person(Hotel hotel,
                  String firstName,
                  String lastName,
                  String email,
                  String phoneNum,
                  String passportNum,
                  Date createdAt,
                  Date updatedAt) {
        super(hotel, createdAt, updatedAt);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNum = phoneNum;
        this.passportNum = passportNum;
    }

    public Person(Person other) {
        super(other);
        this.firstName = other.firstName;
        this.lastName = other.lastName;
        this.email = other.email;
        this.phoneNum = other.phoneNum;
        this.passportNum = other.passportNum;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPhoneNum() {
        return this.phoneNum;
    }

    public String getPassportNum() {
        return this.passportNum;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setPassportNum(String passportNum) {
        this.passportNum = passportNum;
    }
}
