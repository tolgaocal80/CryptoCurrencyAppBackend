package com.tolgaocal80.bayzat.service;

import com.tolgaocal80.bayzat.config.PasswordConfig;
import com.tolgaocal80.bayzat.dto.*;
import com.tolgaocal80.bayzat.entity.Alert;
import com.tolgaocal80.bayzat.entity.Currency;
import com.tolgaocal80.bayzat.entity.User;
import com.tolgaocal80.bayzat.exception.UnsupportedCurrencyCreationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


@Import({CurrencyService.class, AlertService.class, UserService.class, PasswordConfig.class})
@DataJpaTest
@ActiveProfiles("test")
public class AlertServiceTest {

    @Autowired
    private AlertService alertService;
    @Autowired
    private UserService userService;
    @Autowired
    private CurrencyService currencyService;

    private User savedUser;
    private Currency savedCurrency;

    @BeforeEach
    public void initializeUser(){
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("test-user");
        userDTO.setPassword("admin");
        savedUser = userService.save(userDTO);
        savedUser.setAlerts(new HashSet<>());
    }
    @BeforeEach
    public void initializeCurrency() throws UnsupportedCurrencyCreationException {
        CurrencyDTO currencyDTO = new CurrencyDTO();
        currencyDTO.setName("Ether");
        currencyDTO.setSymbol("ET");
        currencyDTO.setCurrentPriceUSD(112);
        savedCurrency = currencyService.save(currencyDTO);
    }


    @Test
    public void shouldAddAlertToUser(){
        AlertDTO alertDTO = new AlertDTO();
        alertDTO.setTargetPrice(12324);

        Alert savedAlert = alertService.addAlertToUser(savedUser.getId(), savedCurrency.getId(), alertDTO);

        assertThat(savedAlert).isIn(savedUser.getAlerts());
    }

    /*
    OTHER SERVICE TESTS.....
     */


}
