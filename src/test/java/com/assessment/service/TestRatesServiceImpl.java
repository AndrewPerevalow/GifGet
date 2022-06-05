package com.assessment.service;

import com.assessment.client.FeignRatesClient;
import com.assessment.model.ExchangeRates;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRatesServiceImpl {

    @Autowired
    private RatesServiceImpl ratesService;

    @MockBean
    private FeignRatesClient feignRatesClient;

    private ExchangeRates latestRates;
    private ExchangeRates historicalRates;

    @Value("${openexchangerates.base}")
    String base;
    @Value("${openexchangerates.app.id}")
    String appId;

    @Before
    public void initRates() {
        int time = 1654464088;
        Map<String, Double> latestValues = new HashMap<>();
        latestValues.put("value1", 1.505678); // <- value1 stays same
        latestValues.put("value2", 1.095345); // <- value2 decreases
        latestValues.put("value3", 0.507346); // <- value3 increases
        latestValues.put(base, 60.955);
        latestValues.put("defaultBase", 60.955);
        latestRates = new ExchangeRates();
        latestRates.setTimestamp(time);
        latestRates.setBase("defaultBase");
        latestRates.setRates(latestValues);

        time = 1654377688;
        Map<String, Double> historicalValues = new HashMap<>();
        historicalValues.put("value1", 1.505678);
        historicalValues.put("value2", 1.194355);
        historicalValues.put("value3", 0.404226);
        historicalValues.put(base, 60.955);
        historicalValues.put("defaultBase", 60.955);
        historicalRates = new ExchangeRates();
        historicalRates.setTimestamp(time);
        historicalRates.setBase("defaultBase");
        historicalRates.setRates(historicalValues);
    }

    @Test
    public void returnCompareRatesRich() {
        Mockito.when(feignRatesClient.getLatestRates(appId)).thenReturn(latestRates);
        Mockito.when(feignRatesClient.getHistoricalRates("2022-06-05",appId)).thenReturn(historicalRates);
        String result = ratesService.compareValues("value3");
        assertEquals("rich", result);
    }

    @Test
    public void returnCompareRatesBroke() {
        Mockito.when(feignRatesClient.getLatestRates(appId)).thenReturn(latestRates);
        Mockito.when(feignRatesClient.getHistoricalRates("2022-06-05",appId)).thenReturn(historicalRates);
        String result = ratesService.compareValues("value2");
        assertEquals("broke", result);
    }

    @Test
    public void returnCompareRatesZero() {
        Mockito.when(feignRatesClient.getLatestRates(appId)).thenReturn(latestRates);
        Mockito.when(feignRatesClient.getHistoricalRates("2022-06-05",appId)).thenReturn(historicalRates);
        String result = ratesService.compareValues("value1");
        assertEquals("zero", result);
    }

    @Test
    public void returnCompareRatesNull() {
        Mockito.when(feignRatesClient.getLatestRates(appId)).thenReturn(latestRates);
        Mockito.when(feignRatesClient.getHistoricalRates("2022-06-05",appId)).thenReturn(historicalRates);
        String result = ratesService.compareValues(null);
        assertEquals("mistake", result);
    }

    @Test
    public void returnCompareRatesMistake() {
        Mockito.when(feignRatesClient.getLatestRates(appId)).thenReturn(latestRates);
        Mockito.when(feignRatesClient.getHistoricalRates("2022-06-05",appId)).thenReturn(historicalRates);
        String result = ratesService.compareValues("unexpectedValue");
        assertEquals("mistake", result);
    }

    @Test
    public void returnCompareRatesBaseChanged() {
        latestRates.getRates().put(base, 63.765);
        Mockito.when(feignRatesClient.getLatestRates(appId)).thenReturn(latestRates);
        Mockito.when(feignRatesClient.getHistoricalRates("2022-06-05",appId)).thenReturn(historicalRates);
        String result = ratesService.compareValues(base);
        assertEquals("zero", result);
    }

}
