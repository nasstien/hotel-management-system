package org.example.hotel.models;

import java.util.Date;

public class Room extends Entity {
    private RoomType type;
    private boolean occupied;

    public Room(Hotel hotel,
                RoomType type,
                boolean occupied,
                Date createdAt,
                Date updatedAt) {
        super(hotel, createdAt, updatedAt);
        this.type = type;
        this.occupied = occupied;
    }

    public Room(Room other) {
        super(other);
        this.type = other.type;
        this.occupied = other.occupied;
    }

    public RoomType getType() {
        return this.type;
    }

    public boolean getOccupied() {
        return this.occupied;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
}
