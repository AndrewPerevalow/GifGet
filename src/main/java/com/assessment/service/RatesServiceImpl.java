package com.assessment.service;

import com.assessment.client.FeignRatesClient;
import com.assessment.model.ExchangeRates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class RatesServiceImpl implements RatesService {

    private ExchangeRates historicalRates;
    private ExchangeRates latestRates;

    private FeignRatesClient ratesClient;

    private DateFormat currentDate;
    private DateFormat yesterdayDate;
    private DateFormat currentDateFromJson;
    private DateFormat yesterdayDateFromJson;

    @Value("${openexchangerates.app.id}")
    String appId;
    @Value("${openexchangerates.base}")
    String base;

    @Autowired
    public RatesServiceImpl(FeignRatesClient ratesClient) {
        this.ratesClient = ratesClient;
    }

    /**
     * Get all latest rates
     * */
    @Override
    public List<String> getAllValues() {
        List<String> values = new ArrayList<>();
        if (latestRates.getRates() != null) {
            values = latestRates.getRates().keySet().stream().toList();
        }
        return values;
    }

    /**
     * Compare values (USD with chosen value)
     * */
    @Override
    public String compareValues(String value) {

        initRates();
        String currentDateFromJson = getCurrentDateFromJsonString();
        String yesterdayDateFromJson = getYesterdayDateFromJsonString();

        String currentDate = getCurrentDateString();
        String yesterdayDate = getYesterdayDateString();

        if (latestRates == null || !currentDate.equals(currentDateFromJson)) {
            latestRates = ratesClient.getLatestRates(appId);
        }

        if (historicalRates == null || !yesterdayDate.equals(yesterdayDateFromJson)) {
            historicalRates = ratesClient.getHistoricalRates(yesterdayDate, appId);
        }
        Double currentValue = getValue(latestRates, value);
        Double yesterdayValue = getValue(historicalRates, value);

        if (currentValue != null && yesterdayValue != null) {
            if (currentValue > yesterdayValue) {
                return "rich";
            } else if (currentValue.equals(yesterdayValue)) {
                return "zero";
            }
            return "broke";
        }
        return "mistake";
    }

    @Override
    public void initRates() {
        if (latestRates == null) {
            latestRates = ratesClient.getLatestRates(appId);
        }
        if (historicalRates == null) {
            historicalRates = ratesClient.getHistoricalRates(getYesterdayDateString(),appId);
        }
    }

    private String getCurrentDateFromJsonString() {
        currentDateFromJson = new SimpleDateFormat("yyyy-MM-dd");
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(latestRates.getTimestamp() * 1000L);
        return currentDateFromJson.format(cal.getTime());
    }

    private String getYesterdayDateFromJsonString() {
        yesterdayDateFromJson = new SimpleDateFormat("yyyy-MM-dd");
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(historicalRates.getTimestamp() * 1000L);
        return yesterdayDateFromJson.format(cal.getTime());
    }

    private String getCurrentDateString() {
        currentDate = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return currentDate.format(date);
    }

    private Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, - 1);
        return cal.getTime();
    }

    private String getYesterdayDateString() {
        yesterdayDate = new SimpleDateFormat("yyyy-MM-dd");
        return yesterdayDate.format(yesterday());
    }

    private Double getValue(ExchangeRates exchangeRates, String value) {
        Map<String, Double> rates;
        Double currentValue = null;
        Double baseValue = null;
        Double defaultValue = null;
        Double result = null;
        if (exchangeRates != null &&  exchangeRates.getRates() != null) {
            rates = exchangeRates.getRates();
            currentValue = rates.get(value);
            baseValue = rates.get(base);
            defaultValue = rates.get(exchangeRates.getBase());
        }
        if (currentValue != null && baseValue != null && defaultValue != null) {
            MathContext context = new MathContext(6, RoundingMode.UP);
            result = (defaultValue/baseValue) * currentValue;
            result = new BigDecimal(result, context).doubleValue();
        }
        return result;
    }


}
