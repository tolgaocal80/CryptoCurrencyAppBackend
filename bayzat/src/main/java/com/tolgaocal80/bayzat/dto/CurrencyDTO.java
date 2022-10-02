package com.tolgaocal80.bayzat.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Date;

// Data transfer object for Currency class
public class CurrencyDTO {

    private long marketRank;

    @NotBlank
    private String name;

    @NotBlank
    private String symbol;

    private long circulatingSupply;

    private long maxSupply;

    @Positive
    @NotNull
    private double currentPriceUSD;

    private Date lastUpdated;

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

    public double getCurrentPriceUSD() {
        return currentPriceUSD;
    }

    public void setCurrentPriceUSD(double currentPriceUSD) {
        this.currentPriceUSD = currentPriceUSD;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
