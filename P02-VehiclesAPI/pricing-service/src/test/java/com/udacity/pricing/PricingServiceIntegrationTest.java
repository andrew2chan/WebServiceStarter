package com.udacity.pricing;

import com.udacity.pricing.domain.price.Price;
import com.udacity.pricing.domain.price.PriceRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PricingServiceIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private PriceRepository priceRepository;

    private Price newPrice;

    @Before
    public void setUp() {
        newPrice = priceRepository.save(new Price("CAD", BigDecimal.valueOf(50000), 1L));
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

    @Test
    public void testThatMicroserviceIsMakingPrices() {
        ResponseEntity<Price> price = testRestTemplate.getForEntity("http://localhost:" + port + "/prices/1", Price.class); //consume api to test

        assertEquals(HttpStatus.OK, price.getStatusCode());
        assertEquals(BigDecimal.valueOf(50000).setScale(2), price.getBody().getPrice());
        assertEquals("CAD", price.getBody().getCurrency());
    }

    @After
    public void tearDown() {
        priceRepository.deleteAll();
    }
}
