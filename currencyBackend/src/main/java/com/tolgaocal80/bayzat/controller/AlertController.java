package com.tolgaocal80.bayzat.controller;


import com.tolgaocal80.bayzat.dto.AlertDTO;
import com.tolgaocal80.bayzat.dto.AlertUpdateDTO;
import com.tolgaocal80.bayzat.entity.Alert;
import com.tolgaocal80.bayzat.service.AlertService;
import com.tolgaocal80.bayzat.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/alerts")
public class AlertController {

    private final AlertService alertService;
    private final UserService userService;

    public AlertController(AlertService alertService, UserService userService) {
        this.alertService = alertService;
        this.userService = userService;
    }


    // Returns all alerts of the users active or in-active alerts
    @GetMapping("/")
    public ResponseEntity<List<Alert>> getUserAlertsById(
            @RequestParam(name = "userId") long userId) {
        return ResponseEntity.ok(userService.getUsersAlerts(userId));
    }



    // Adds alert to the user, requires valid AlertDTO
    @PostMapping("/add")
    public ResponseEntity<Alert> addAlertToUser(
            @RequestParam(name = "userId") long userId,
            @RequestParam(name = "currencyId") long currencyId,
            @Valid @RequestBody AlertDTO alertDTO) {
        return ResponseEntity.ok(alertService.addAlertToUser(userId, currencyId, alertDTO));
    }

    // Updates alert with given AlertDTO which has targetPrice field
    @PutMapping("/update")
    public ResponseEntity<Alert> updateAlert(
            @RequestParam(name = "alertId") long alertId,
            @Valid @RequestBody AlertUpdateDTO alertUpdateDTO) {
        return ResponseEntity.ok(alertService.updateAlert(alertId, alertUpdateDTO));
    }


    // Detach relations with user and, sets alert status to CANCELLED and sets in-active
    // which means will not be available for other endpoint requests.
    @DeleteMapping("/delete")
    public ResponseEntity<Alert> deleteAlert(
            @RequestParam(name = "alertId") long alertId){
        return ResponseEntity.ok(alertService.cancelAlert(alertId));
    }


    // Same as "/delete" endpoint
    @DeleteMapping("/cancel")
    public ResponseEntity<Alert> cancelAlert(
            @RequestParam(name = "alertId") long alertId){
        return ResponseEntity.ok(alertService.cancelAlert(alertId));
    }

    // Sets alert status to ACKED. Once alert has triggered to stop print out, call this endpoint
    // If alert is not triggered and this endpoint called, then alert will be cancelled.
    @PutMapping("/acknowledge")
    public ResponseEntity<Alert> acknowledgeAlert(
            @RequestParam(name = "alertId") long alertId) {
        return ResponseEntity.ok(alertService.acknowledgeAlert(alertId));
    }

    // Returns if alert is triggered
    @GetMapping("/isTriggered")
    public ResponseEntity<Boolean> checkIfTriggered(
            @RequestParam(name = "alertId") long alertId) {
        return ResponseEntity.ok(alertService.checkAlertIfTriggered(alertId));
    }

}