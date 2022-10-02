package com.tolgaocal80.bayzat.service;

import com.tolgaocal80.bayzat.dto.CurrencyDTO;
import com.tolgaocal80.bayzat.dto.CurrencyUpdateDTO;
import com.tolgaocal80.bayzat.entity.Currency;
import com.tolgaocal80.bayzat.exception.UnsupportedCurrencyCreationException;
import com.tolgaocal80.bayzat.repo.AlertRepository;
import com.tolgaocal80.bayzat.repo.CurrencyRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final AlertRepository alertRepository;

    public CurrencyService(CurrencyRepository currencyRepository, AlertRepository alertRepository) {
        this.currencyRepository = currencyRepository;
        this.alertRepository = alertRepository;
    }

    public Currency save(CurrencyDTO currencyDTO) throws UnsupportedCurrencyCreationException {

        // If any currency has symbol which given in the list, tried to be added then throws UnsupportedCreationException
        switch (currencyDTO.getSymbol()) {
            case "ETH" -> throw new UnsupportedCurrencyCreationException("ETH");
            case "LTC" -> throw new UnsupportedCurrencyCreationException("LTC");
            case "ZKN" -> throw new UnsupportedCurrencyCreationException("ZKN");
            case "MRD" -> throw new UnsupportedCurrencyCreationException("MRD");
            case "LPR" -> throw new UnsupportedCurrencyCreationException("LPR");
            case "GBZ" -> throw new UnsupportedCurrencyCreationException("GBZ");
        }

        Currency currency = new Currency();

        currency.setName(currencyDTO.getName());
        currency.setSymbol(currencyDTO.getSymbol());
        currency.setCirculatingSupply(currencyDTO.getCirculatingSupply());
        currency.setCurrentPriceUSD(currencyDTO.getCurrentPriceUSD());
        currency.setMaxSupply(currencyDTO.getMaxSupply());
        currency.setLastUpdated(new Date());
        currency.setMarketRank(currencyDTO.getMarketRank());

        return currencyRepository.save(currency);
    }

    public Currency findByCurrencyName(String s){
        var opt = currencyRepository.findByNameAndActiveTrue(s);
        return opt.orElseThrow(() -> {
            throw  new IllegalArgumentException("Currency not found");
        });
    }

    public List<Currency> findAll(){
        return currencyRepository.findAll();
    }

    public Currency findById(Long id){
        var optionalCurrency = currencyRepository.findByIdAndActiveTrue(id);
        return optionalCurrency.orElseThrow(() -> {
            throw new IllegalArgumentException("Currency not found");
        });
    }

    // If a currency removed then all alerts of it also will be "in-active".
    public Currency remove(long id){
        Currency currency = this.findById(id);
        currency.setActive(false);
        currency.getAlerts().forEach(alert -> {
            alert.setActive(false);
            alert.setCurrency(null);
            alert.setUser(null);

            alertRepository.save(alert);
        });
        return currencyRepository.save(currency);
    }

    public Currency update(long id, CurrencyUpdateDTO currencyUpdateDTO){
        Currency currency = this.findById(id);
        currency.setName(currencyUpdateDTO.getName());
        currency.setSymbol(currencyUpdateDTO.getSymbol());
        currency.setCurrentPriceUSD(currencyUpdateDTO.getCurrentPrice());
        currency.setMarketRank(currencyUpdateDTO.getMarketRank());
        currency.setMaxSupply(currencyUpdateDTO.getMaxSupply());
        currency.setCirculatingSupply(currencyUpdateDTO.getCirculatingSupply());

        return currencyRepository.save(currency);
    }


    public List<Currency> findByCurrencySymbol(String currencySym) {
        return currencyRepository.findByActiveTrueAndSymbolContains(currencySym).orElseThrow(()
                -> new IllegalArgumentException("Not found with given symbol"));

    }

}
