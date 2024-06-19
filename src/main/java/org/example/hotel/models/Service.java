package org.example.hotel.models;

import java.util.Date;

public class Service extends Entity {
    private String name;
    private String description;
    private String category;
    private String startTime;
    private String endTime;
    private Double price;
    private boolean available;

    public Service(Hotel hotel,
                   String name,
                   String description,
                   String category,
                   String startTime,
                   String endTime,
                   Double price,
                   boolean available,
                   Date createdAt,
                   Date updatedAt) {
        super(hotel, createdAt, updatedAt);
        this.name = name;
        this.description = description;
        this.category = category;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
        this.available = available;
    }

    public Service(Service other) {
        super(other);
        this.name = other.name;
        this.description = other.description;
        this.category = other.category;
        this.startTime = other.startTime;
        this.endTime = other.endTime;
        this.price = other.price;
        this.available = other.available;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getCategory() {
        return this.category;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public Double getPrice() {
        return this.price;
    }

    public boolean getAvailable() {
        return this.available;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
