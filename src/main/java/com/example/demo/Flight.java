package com.example.demo;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @DateTimeFormat(pattern = "yyyy-MMM-dd HH:mm")
    private Date date;
    private String departsFrom;
    private String arrivesAt;
    private String price;
    private String airline;

    public Flight(Date date, String departsFrom, String arrivesAt, String price, String airline) {
        this.date = date;
        this.departsFrom = departsFrom;
        this.arrivesAt = arrivesAt;
        this.price = price;
        this.airline = airline;
    }

    public Flight() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDepartsFrom() {
        return departsFrom;
    }

    public void setDepartsFrom(String departsFrom) {
        this.departsFrom = departsFrom;
    }

    public String getArrivesAt() {
        return arrivesAt;
    }

    public void setArrivesAt(String arrivesAt) {
        this.arrivesAt = arrivesAt;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }
}
