package org.example.hotel.models;

import java.util.Date;

public class Booking extends Entity {
    private Room room;
    private Guest guest;
    private Payment payment;

    public Booking(Hotel hotel,
                   Room room,
                   Guest guest,
                   Payment payment,
                   Date createdAt,
                   Date updatedAt) {
        super(hotel, createdAt, updatedAt);
        this.room = room;
        this.guest = guest;
        this.payment = payment;
    }

    public Booking(Booking other) {
        super(other);
        this.room = other.room;
        this.guest = other.guest;
        this.payment = other.payment;
    }

    public Room getRoom() {
        return this.room;
    }

    public Guest getGuest() {
        return this.guest;
    }

    public Payment getPayment() {
        return this.payment;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
