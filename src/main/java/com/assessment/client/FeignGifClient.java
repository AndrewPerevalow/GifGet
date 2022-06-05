package com.assessment.client;

import com.assessment.model.Gifs;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name="gifClient", url="${giphy.url.main}")
public interface FeignGifClient {

    @GetMapping(value = "/random", consumes = MediaType.APPLICATION_JSON_VALUE)
    Gifs getRandomGif(@RequestParam("api_key") String apiKey,
                      @RequestParam("tag") String tag);

}
