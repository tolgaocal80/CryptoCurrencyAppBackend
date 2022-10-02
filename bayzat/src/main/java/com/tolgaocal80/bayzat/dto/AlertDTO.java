package com.tolgaocal80.bayzat.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

// Data transfer object of Alert class
public class AlertDTO {

    @NotNull
    @Positive
    private double targetPrice;

    public double getTargetPrice() {
        return targetPrice;
    }

    public void setTargetPrice(double targetPrice) {
        this.targetPrice = targetPrice;
    }
}
