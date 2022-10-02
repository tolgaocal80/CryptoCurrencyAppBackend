package com.tolgaocal80.bayzat.repository;


import com.tolgaocal80.bayzat.entity.Alert;
import com.tolgaocal80.bayzat.entity.AlertStatus;
import com.tolgaocal80.bayzat.repo.AlertRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class AlertRepositoryTest {

    @Autowired
    private AlertRepository alertRepository;

    @Test
    public void shouldReturnAlertsWhichActiveTrueAndStatusEquals(){

        AlertStatus newStatus = AlertStatus.NEW;

        Optional<List<Alert>> alertListOpt = alertRepository.getAlertsByActiveTrueAndAlertStatusEquals(newStatus);

        if (alertListOpt.isPresent()){
            if (alertListOpt.get().size()>0){
                List<Alert> alerts = alertListOpt.get();
                assert (alerts.stream().allMatch(alert -> (alert.isActive() && alert.getAlertStatus().equals(newStatus))));
            }
        }


    }


}
