package com.assessment.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
public class ExchangeRates {
    private int timestamp;
    private String base;
    private Map<String, Double> rates;
}
