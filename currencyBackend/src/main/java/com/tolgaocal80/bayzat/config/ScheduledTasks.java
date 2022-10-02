package com.tolgaocal80.bayzat.config;

import com.tolgaocal80.bayzat.entity.Alert;
import com.tolgaocal80.bayzat.entity.AlertStatus;
import com.tolgaocal80.bayzat.repo.AlertRepository;
import com.tolgaocal80.bayzat.service.AlertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class ScheduledTasks {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    private final AlertService alertService;

    private final AlertRepository alertRepository;

    public ScheduledTasks(AlertService alertService, AlertRepository alertRepository) {
        this.alertService = alertService;
        this.alertRepository = alertRepository;
    }

    // Checks EVERY 30 seconds if any of alerts status has triggered.
    @Scheduled(fixedDelay = 30000)
    public void scheduleTriggeredAlerts(){


        Optional<List<Alert>> allAlerts = alertRepository.getAlertsByActiveTrue();

        // If alert status if not acked AND alerts trigger price is equal to currencies current price
        // then sets alerts status to TRIGGERED.
        allAlerts.ifPresent(alerts -> {
            alerts.forEach(alert -> {
                if (alert.getTargetPrice() == alert.getCurrency().getCurrentPriceUSD() &&
                !alert.getAlertStatus().equals(AlertStatus.ACKED)){
                    alert.setAlertStatus(AlertStatus.TRIGGERED);
                    alertRepository.save(alert);
                }
            });
        });

        // Returns triggered alarms.
        List<Alert> triggeredAlerts = alertService.getTriggeredAlerts();

        // If has any then print to the console.
        if (triggeredAlerts.size() > 0){
            triggeredAlerts.forEach(alert -> {
                logger.info("Price alert triggered for : "+ alert.getCurrency().getName() +
                        " target price : "+ alert.getTargetPrice());
            });
        }

    }

}
