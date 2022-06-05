package com.assessment.service;

import com.assessment.client.FeignGifClient;
import com.assessment.model.Gifs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class GifServiceImpl implements GifService {

    private FeignGifClient gifClient;
    private Gifs gifs;

    @Value("${giphy.api.key}")
    private String apiKey;

    @Autowired
    public GifServiceImpl(FeignGifClient gifClient) {
        this.gifClient= gifClient;
    }

    /**
     * Get random gif from Giphy.com with current(rich, broke or zero) tag
     * */
    @Override
    public String getGif(String tag) {
        gifs = gifClient.getRandomGif(this.apiKey, tag);
        String result = gifs.getData().getImages().getOriginal().get("url");
        return result;
    }
}
