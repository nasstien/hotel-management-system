package org.example.hotel.models;

import java.util.Date;

public class ServiceOrder extends Entity {
    private Service service;
    private Booking booking;
    private Payment payment;
    private String serviceTime;
    private Date serviceDate;

    public ServiceOrder(Hotel hotel,
                        Service service,
                        Booking booking,
                        Payment payment,
                        String serviceTime,
                        Date serviceDate,
                        Date createdAt,
                        Date updatedAt) {
        super(hotel, createdAt, updatedAt);
        this.service = service;
        this.booking = booking;
        this.payment = payment;
        this.serviceTime = serviceTime;
        this.serviceDate = serviceDate;
    }

    public ServiceOrder(ServiceOrder other) {
        super(other);
        this.service = other.service;
        this.booking = other.booking;
        this.payment = other.payment;
        this.serviceTime = other.serviceTime;
        this.serviceDate = other.serviceDate;
    }

    public Service getService() {
        return this.service;
    }

    public Booking getBooking() {
        return this.booking;
    }

    public Payment getPayment() {
        return this.payment;
    }

    public String getServiceTime() {
        return this.serviceTime;
    }

    public Date getServiceDate() {
        return this.serviceDate;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }

    public void setServiceDate(Date serviceDate) {
        this.serviceDate = serviceDate;
    }
}
