package com.assessment.controller;

import com.assessment.service.GifService;
import com.assessment.service.RatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class MainController {

    private RatesService ratesService;
    private GifService gifService;
    @Value("${giphy.rich}")
    private String richTag;
    @Value("${giphy.broke}")
    private String brokeTag;
    @Value("${giphy.zero}")
    private String zeroTag;
    @Value("${giphy.mistake}")
    private String mistakeTag;

    @Autowired
    public MainController(RatesService ratesService, GifService gifService) {
        this.ratesService = ratesService;
        this.gifService = gifService;
    }

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("startPage", getAllValues());
        model.addAttribute("title", "Values - test assessment");
        return "main";
    }

    /**
     * Get all values
     * */
    @GetMapping("/getvalues")
    public @ResponseBody List<String> getAllValues() {
        return ratesService.getAllValues();
    }

    /**
     * Get gif for client after compare rates
     * */
    @GetMapping("/getcurrentgif/{getValue}")
    public String getGif(@PathVariable String getValue, Model model) {
        String result;
        String gifAfterCompareTag = null;
        String gifTag;
        if (getValue != null) {
            gifAfterCompareTag = ratesService.compareValues(getValue);
        }
        assert gifAfterCompareTag != null;
        if (gifAfterCompareTag.equals(richTag)) {
            gifTag = richTag;
        } else if (gifAfterCompareTag.equals(brokeTag)) {
            gifTag = brokeTag;
        } else if (gifAfterCompareTag.equals(zeroTag)) {
            gifTag = zeroTag;
        } else {
            gifTag = mistakeTag;
        }
        result = gifService.getGif(gifTag);
        model.addAttribute("getTag", gifTag);
        model.addAttribute("getGif", result);
        return "main";
    }
}
