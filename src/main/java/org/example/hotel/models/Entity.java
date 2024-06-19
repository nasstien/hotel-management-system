package org.example.hotel.models;

import java.util.Date;

public abstract class Entity {
    protected Hotel hotel;
    protected Date createdAt;
    protected Date updatedAt;

    protected Entity(Hotel hotel, Date createdAt, Date updatedAt) {
        this.hotel = hotel;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    protected Entity(Entity other) {
        this.hotel = other.hotel;
        this.createdAt = other.createdAt;
        this.updatedAt = other.updatedAt;
    }

    public Hotel getHotel() {
        return this.hotel;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
