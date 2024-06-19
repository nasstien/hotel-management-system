package org.example.hotel.models;

import java.util.Date;

public class RoomType extends Entity {
    private String name;
    private String description;
    private Double pricePerNight;
    private int capacity;

    public RoomType(Hotel hotel,
                    String name,
                    String description,
                    Double pricePerNight,
                    int capacity,
                    Date createdAt,
                    Date updatedAt) {
        super(hotel, createdAt, updatedAt);
        this.name = name;
        this.description = description;
        this.pricePerNight = pricePerNight;
        this.capacity = capacity;
    }

    public RoomType(RoomType other) {
        super(other);
        this.name = other.name;
        this.description = other.description;
        this.pricePerNight = other.pricePerNight;
        this.capacity = other.capacity;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Double getPricePerNight() {
        return this.pricePerNight;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPricePerNight(Double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
