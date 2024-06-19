package org.example.hotel.models;

import java.util.Date;

public class Hotel {
    private String name;
    private String address;
    private String contactNum;
    private Date createdAt;
    private Date updatedAt;

    public Hotel(String name,
                 String address,
                 String contactNum,
                 Date createdAt,
                 Date updatedAt) {
        this.name = name;
        this.address = address;
        this.contactNum = contactNum;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Hotel(Hotel other) {
        this.name = other.name;
        this.address = other.address;
        this.contactNum = other.contactNum;
        this.createdAt = other.createdAt;
        this.updatedAt = other.updatedAt;
    }

    public String getName() {
        return this.name;
    }

    public String getAddress() {
        return this.address;
    }

    public String getContactNum() {
        return this.contactNum;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
