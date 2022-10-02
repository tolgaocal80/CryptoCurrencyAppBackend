package com.tolgaocal80.bayzat.controller;

import com.tolgaocal80.bayzat.dto.CurrencyDTO;
import com.tolgaocal80.bayzat.dto.CurrencyUpdateDTO;
import com.tolgaocal80.bayzat.entity.Currency;
import com.tolgaocal80.bayzat.exception.UnsupportedCurrencyCreationException;
import com.tolgaocal80.bayzat.service.CurrencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/currencies")
public class CurrencyController {

    private final CurrencyService currencyService;


    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    // Returns all currencies
    @GetMapping("")
    public ResponseEntity<List<Currency>> getCurrencies(){
        return ResponseEntity.ok(currencyService.findAll());
    }

    // Return active currencies with given specific name
    @GetMapping("/by-currency-name")
    public ResponseEntity<Currency> searchCurrencyByName(
            @RequestParam(name = "name") String currencyName){
        return ResponseEntity.ok(currencyService.findByCurrencyName(currencyName));
    }

    // Returns active currencies with given specific symbol
    @GetMapping("/by-currency-symbol")
    public ResponseEntity<List<Currency>> searchCurrencyBySymbol(
            @RequestParam(name = "symbol") String currencySym){
        return ResponseEntity.ok(currencyService.findByCurrencySymbol(currencySym));
    }

    // Adds currency with given id to the database, ONLY admins are allowed
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<Currency> addCurrency(@Valid @RequestBody CurrencyDTO currencyDTO) throws UnsupportedCurrencyCreationException {
        return ResponseEntity.ok(currencyService.save(currencyDTO));
    }


    // Deletes currency with given id from the database, ONLY admins are allowed
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete")
    public ResponseEntity<Currency> removeCurrency(@RequestParam(name = "currencyId") long id){
        return ResponseEntity.ok(currencyService.remove(id));
    }

    // Updates currency with given id in the database,requires valid CurrencyDTO, ONLY admins are allowed
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<Currency> updateCurrency(
            @RequestParam(name = "currencyId") long id,
            @Valid @RequestBody CurrencyUpdateDTO currencyUpdateDTO){
        return ResponseEntity.ok(currencyService.update(id, currencyUpdateDTO));
    }


}
