package com.tolgaocal80.bayzat.service;

import com.tolgaocal80.bayzat.dto.AlertDTO;
import com.tolgaocal80.bayzat.dto.AlertUpdateDTO;
import com.tolgaocal80.bayzat.entity.Alert;
import com.tolgaocal80.bayzat.entity.AlertStatus;
import com.tolgaocal80.bayzat.entity.Currency;
import com.tolgaocal80.bayzat.entity.User;
import com.tolgaocal80.bayzat.repo.AlertRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AlertService {

    private final CurrencyService currencyService;
    private final UserService userService;
    private final AlertRepository alertRepository;

    public AlertService(CurrencyService currencyService, UserService userService, AlertRepository alertRepository) {
        this.currencyService = currencyService;
        this.userService = userService;
        this.alertRepository = alertRepository;
    }

    public Alert addAlertToUser(long userId, long currencyId, AlertDTO alertDTO){
        Currency currency = currencyService.findById(currencyId);
        User user = userService.findById(userId);

        Alert alert = new Alert();
        alert.setCreatedPrice(currency.getCurrentPriceUSD());
        alert.setAlertStatus(AlertStatus.NEW);
        alert.setTargetPrice(alertDTO.getTargetPrice());
        alert.setCurrency(currency);
        alert.setUser(user);

        user.getAlerts().add(alert);

        return alertRepository.save(alert);
    }

    public Alert findById(long id){
        return alertRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("Alert not found");
        });
    }

    public Alert cancelAlert(long id){
        Alert alert = this.findById(id);
        alert.setAlertStatus(AlertStatus.CANCELLED);
        alert.getUser().getAlerts().remove(alert);

        alert.setCurrency(null);
        alert.setActive(false);
        return alertRepository.save(alert);
    }


    public Alert updateAlert(long alertId, AlertUpdateDTO dto){
        Alert alert = this.findById(alertId);
        if (alert.isActive() && alert.getAlertStatus() != AlertStatus.CANCELLED){
            alert.setTargetPrice(dto.getTargetPrice());
        }
        return alertRepository.save(alert);
    }



    public Alert acknowledgeAlert(long alertId){
        Alert alert = this.findById(alertId);
        if (alert.getAlertStatus().equals(AlertStatus.TRIGGERED)){
            alert.setAlertStatus(AlertStatus.ACKED);
            return alertRepository.save(alert);
        }else {
            return this.cancelAlert(alertId);
        }
    }


    public boolean checkAlertIfTriggered(long id){
        Alert alert = this.findById(id);
        return this.getTriggeredAlerts().contains(alert);
    }

    public List<Alert> getTriggeredAlerts(){
        Optional<List<Alert>> optionalAlerts =
                alertRepository.getAlertsByActiveTrueAndAlertStatusEquals(AlertStatus.TRIGGERED);

        return optionalAlerts.orElse(Collections.emptyList());
    }

    public List<Alert> getAllAlerts(){
        return alertRepository.findAll();
    }




}
