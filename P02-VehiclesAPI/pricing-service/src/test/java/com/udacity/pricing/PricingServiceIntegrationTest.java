package com.udacity.pricing;

import com.udacity.pricing.domain.price.Price;
import com.udacity.pricing.domain.price.PriceRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PricingServiceIntegrationTest {
    @Autowired
    private PriceRepository priceRepository;

    private Price newPrice;

    @Before
    public void setUp() {
        newPrice = priceRepository.save(new Price("CAD", BigDecimal.valueOf(50000L), 1L));
    }

    @Test
    public void testThatPriceIsSaved() {
        assertNotNull(newPrice.getPrice());
        assertNotNull(newPrice.getCurrency());
        assertNotNull(newPrice.getVehicleId());
    }

    @Test
    public void testThatPriceIsReturned() {
        assertEquals(priceRepository.findById(1L).isEmpty(), false);
    }

    @Test
    public void testThatPriceIsNotReturnred() {
        assertEquals(priceRepository.findById(3L).isEmpty(), true);
    }
}
