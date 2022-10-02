package com.tolgaocal80.bayzat.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "CURRENCIES")
public class Currency extends BaseEntity{

    @Column(name = "MARKET_RANK")
    private long marketRank;

    @Column(name = "NAME")
    private String name;
    @Column(name = "SYMBOL")
    private String symbol;

    @Column(name = "CIRCULATING_SUPPLY")
    private long circulatingSupply;

    @Column(name = "MAX_SUPPLY")
    private long maxSupply;

    @Column(name = "PRICE_USD")
    private double currentPriceUSD;

    @Column(name = "LAST_UPDATED")
    private Date lastUpdated;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "currency")
    @JsonBackReference
    private Set<Alert> alerts;

    public long getMarketRank() {
        return marketRank;
    }

    public void setMarketRank(long marketRank) {
        this.marketRank = marketRank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public long getCirculatingSupply() {
        return circulatingSupply;
    }

    public void setCirculatingSupply(long circulatingSupply) {
        this.circulatingSupply = circulatingSupply;
    }

    public long getMaxSupply() {
        return maxSupply;
    }

    public void setMaxSupply(long maxSupply) {
        this.maxSupply = maxSupply;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public double getCurrentPriceUSD() {
        return currentPriceUSD;
    }

    public void setCurrentPriceUSD(double currentPriceUSD) {
        this.currentPriceUSD = currentPriceUSD;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Set<Alert> getAlerts() {
        return alerts;
    }

    public void setAlerts(Set<Alert> alerts) {
        this.alerts = alerts;
    }
}
