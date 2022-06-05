package com.assessment.service;

import java.util.List;

public interface RatesService {
        List<String> getAllValues();
        String compareValues(String value);
        void initRates();
}
