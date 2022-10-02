package com.tolgaocal80.bayzat.service;

import com.tolgaocal80.bayzat.dto.CurrencyDTO;
import com.tolgaocal80.bayzat.exception.UnsupportedCurrencyCreationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Import(CurrencyService.class)
@DataJpaTest
@ActiveProfiles("test")
public class CurrencyServiceTest {

    @Autowired
    private CurrencyService currencyService;


    // Should throw UnsupportedCurrencyCreationException
    @Test
    public void shouldNotSaveCurrency() throws UnsupportedCurrencyCreationException {
        CurrencyDTO currencyDTO = new CurrencyDTO();
        currencyDTO.setName("Ethereum");
        currencyDTO.setSymbol("ETH");
        currencyDTO.setCurrentPriceUSD(1233);

        assertThrows(UnsupportedCurrencyCreationException.class,
                ()->{currencyService.save(currencyDTO);});
    }

    /*
    OTHER SERVICE TESTS...
     */


}
