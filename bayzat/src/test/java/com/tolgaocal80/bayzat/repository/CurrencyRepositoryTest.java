package com.tolgaocal80.bayzat.repository;


import com.tolgaocal80.bayzat.entity.Alert;
import com.tolgaocal80.bayzat.entity.Currency;
import com.tolgaocal80.bayzat.repo.AlertRepository;
import com.tolgaocal80.bayzat.repo.CurrencyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.atomicIntegerFieldUpdater;

@DataJpaTest
@ActiveProfiles("test")
public class CurrencyRepositoryTest {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Test
    public void shouldReturnActiveCurrenciesWhichHasAlert(){
        Optional<List<Currency>> currenciesOpt =
                currencyRepository.getAllActiveCurrenciesWithAlerts();

        if(currenciesOpt.isPresent()){
            if (currenciesOpt.get().size() > 0){
                List<Currency> currencies = currencyRepository.getAllActiveCurrenciesWithAlerts().get();
                assert(currencies.stream().allMatch(currency -> (currency.getAlerts().size() > 0) && currency.isActive()));
            }
        }
    }


}
