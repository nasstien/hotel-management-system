package org.example.hotel.models;

import java.util.Date;

public class Guest extends Person {
    private Date checkInDate;
    private Date checkOutDate;

    public Guest(Hotel hotel,
                 String firstName,
                 String lastName,
                 String email,
                 String phoneNum,
                 String passportNum,
                 Date checkInDate,
                 Date checkOutDate,
                 Date createdAt,
                 Date updatedAt) {
        super(hotel, firstName, lastName, email, phoneNum, passportNum, createdAt, updatedAt);
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public Guest(Guest other) {
        super(other);
        this.checkInDate = other.checkInDate;
        this.checkOutDate = other.checkOutDate;
    }

    public Date getCheckInDate() {
        return this.checkInDate;
    }

    public Date getCheckOutDate() {
        return this.checkOutDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
}
