package com.assessment.service;


import com.assessment.client.FeignGifClient;
import com.assessment.model.Gifs;
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
public class TestGifServiceImpl {

    @Autowired
    private GifServiceImpl gifService;

    @MockBean
    private FeignGifClient gifClient;

    private Gifs gifs;

    @Value("${giphy.api.key}")
    private String apiKey;

    @Before
    public void initGif() {
        String url = "someGifUrl";
        Map<String, String> map = new HashMap<>();
        map.put("url", url);
        gifs = new Gifs();
        Gifs data = new Gifs();
        gifs.setData(data);
        data.setImages(new Gifs.Images());
        data.getImages().setOriginal(map);
    }

    @Test
    public void returnGif() {
        Mockito.when(gifClient.getRandomGif(apiKey, "rich"))
               .thenReturn(gifs);
        String result = gifService.getGif("rich");
        assertEquals("someGifUrl", result);
    }
}
