package com.assessment.client;

import com.assessment.model.ExchangeRates;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "ratesClient", url = "${openexchangerates.url.main}")
public interface FeignRatesClient {

    @GetMapping("/latest.json")
    ExchangeRates getLatestRates(@RequestParam("app_id") String appId);

    @GetMapping("/historical/{date}.json")
    ExchangeRates getHistoricalRates(@PathVariable String date,
                                     @RequestParam("app_id") String appId);

}
