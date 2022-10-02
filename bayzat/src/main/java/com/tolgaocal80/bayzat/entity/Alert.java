package com.tolgaocal80.bayzat.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "ALERTS")
public class Alert extends BaseEntity{
    @Column(name = "TARGET_PRICE")
    private double targetPrice;

    @Column(name = "CREATED_PRICE")
    private double createdPrice;

    @Column(name = "ALERT_STATUS")
    private AlertStatus alertStatus;

    // Alerts can only have one specific Currency, or a currency can have multiple alerts
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE,
    CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "CURRENCY_ALERTS",
        joinColumns = {@JoinColumn(name = "ALERT_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "CURRENCY_ID", referencedColumnName = "ID")}
    )
    @JsonManagedReference
    private Currency currency;

    // Users can have multiple alerts but a specific alert can have only one User
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "USERS_ALERTS",
            joinColumns = {@JoinColumn(name = "ALERT_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")})
    @JsonManagedReference
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getTargetPrice() {
        return targetPrice;
    }

    public void setTargetPrice(double targetPrice) {
        this.targetPrice = targetPrice;
    }

    public double getCreatedPrice() {
        return createdPrice;
    }

    public void setCreatedPrice(double createdPrice) {
        this.createdPrice = createdPrice;
    }

    public AlertStatus getAlertStatus() {
        return alertStatus;
    }

    public void setAlertStatus(AlertStatus alertStatus) {
        this.alertStatus = alertStatus;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
