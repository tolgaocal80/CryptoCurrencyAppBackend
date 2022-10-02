package com.tolgaocal80.bayzat.repo;

import com.tolgaocal80.bayzat.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    Optional<Currency> findByNameAndActiveTrue(String name);

    Optional<Currency> findByIdAndActiveTrue(Long id);

    Optional<List<Currency>> findByActiveTrueAndSymbolContains(String symbol);


    @Query(value = "select c from Currency c where c.alerts.size >= 1 and c.active = true")
    Optional<List<Currency>> getAllActiveCurrenciesWithAlerts();




}
