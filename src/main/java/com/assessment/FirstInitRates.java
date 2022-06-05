package com.assessment;

import com.assessment.service.RatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class FirstInitRates {

    private RatesService ratesService;

    @Autowired
    public FirstInitRates(RatesService ratesService) {
        this.ratesService = ratesService;
    }

    @PostConstruct
    public void firstInit() {
        ratesService.initRates();
    }
}
