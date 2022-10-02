package com.tolgaocal80.bayzat.repo;

import com.tolgaocal80.bayzat.entity.Alert;
import com.tolgaocal80.bayzat.entity.AlertStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {

    Optional<List<Alert>> getAlertsByActiveTrueAndAlertStatusEquals(AlertStatus alertStatus);

    Optional<List<Alert>> getAlertsByActiveTrue();



}
