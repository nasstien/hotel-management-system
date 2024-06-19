package org.example.hotel.models;

import org.example.hotel.enums.PaymentMethod;

import java.util.Date;

public class Payment extends Entity {
    private Guest guest;
    private Double totalSum;
    private Double roomCharges;
    private Double serviceCharges;
    private PaymentMethod paymentMethod;
    private boolean paid;

    public Payment(Hotel hotel,
                   Guest guest,
                   Double roomCharges,
                   Double serviceCharges,
                   PaymentMethod paymentMethod,
                   boolean paid,
                   Date createdAt,
                   Date updatedAt) {
        super(hotel, createdAt, updatedAt);
        this.guest = guest;
        this.roomCharges = roomCharges;
        this.serviceCharges = serviceCharges;
        this.paymentMethod = paymentMethod;
        this.paid = paid;
        this.totalSum = calcTotalSum();
    }

    public Payment(Payment other) {
        super(other);
        this.guest = other.guest;
        this.totalSum = other.totalSum;
        this.roomCharges = other.roomCharges;
        this.serviceCharges = other.serviceCharges;
        this.paymentMethod = other.paymentMethod;
        this.paid = other.paid;
    }

    public Guest getGuest() {
        return this.guest;
    }

    public Double getTotalSum() {
        return this.totalSum;
    }

    public Double getRoomCharges() {
        return this.roomCharges;
    }

    public Double getServiceCharges() {
        return this.serviceCharges;
    }

    public PaymentMethod getPaymentMethod() {
        return this.paymentMethod;
    }

    public boolean getPaid() {
        return this.paid;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public void setTotalSum(Double totalSum) {
        this.totalSum = totalSum;
    }

    public void setRoomCharges(Double roomCharges) {
        this.roomCharges = roomCharges;
    }

    public void setServiceCharges(Double serviceCharges) {
        this.serviceCharges = serviceCharges;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public Double calcTotalSum() {
        return this.serviceCharges != null ? this.roomCharges + this.serviceCharges : this.roomCharges;
    }
}
