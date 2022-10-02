package com.tolgaocal80.bayzat.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

// Data transfer object for updating Alert classes objects
public class AlertUpdateDTO {

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
