package com.tolgaocal80.bayzat.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

// Data transfer object for updating Currency classes objects
public class CurrencyUpdateDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String symbol;

    @NotNull
    @Positive
    private double currentPrice;

    @NotNull
    @Positive
    private long marketRank;

    @NotNull
    @Positive
    private long maxSupply;

    @NotNull
    @Positive
    private long circulatingSupply;

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

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public long getMarketRank() {
        return marketRank;
    }

    public void setMarketRank(long marketRank) {
        this.marketRank = marketRank;
    }

    public long getMaxSupply() {
        return maxSupply;
    }

    public void setMaxSupply(long maxSupply) {
        this.maxSupply = maxSupply;
    }

    public long getCirculatingSupply() {
        return circulatingSupply;
    }

    public void setCirculatingSupply(long circulatingSupply) {
        this.circulatingSupply = circulatingSupply;
    }
}
