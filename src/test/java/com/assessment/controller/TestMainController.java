package com.assessment.controller;


import com.assessment.service.GifService;
import com.assessment.service.RatesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(MainController.class)
public class TestMainController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatesService ratesService;
    @MockBean
    private GifService gifService;

    @Value("${giphy.rich}")
    private String richTag;
    @Value("${giphy.broke}")
    private String brokeTag;
    @Value("${giphy.zero}")
    private String zeroTag;
    @Value("${giphy.mistake}")
    private String mistakeTag;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void returnAllValues() throws Exception {
        List<String> values = List.of("testValue");
        Mockito.when(ratesService.getAllValues())
               .thenReturn(values);
        mockMvc.perform(MockMvcRequestBuilders.get("/getvalues")
                                              .content(objectMapper.writeValueAsString(values))
                                              .accept(MediaType.APPLICATION_JSON))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$[0]")
                                               .value("testValue"));
    }

    @Test
    public void returnNullListOfValues() throws Exception {
        Mockito.when(ratesService.getAllValues())
               .thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/getvalues")
                                              .content(objectMapper.writeValueAsString(null))
                                              .accept(MediaType.APPLICATION_JSON))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$[0]")
                                               .doesNotExist());
    }

    @Test
    public void returnRichGif() throws Exception {
        Mockito.when(ratesService.compareValues("anyValue"))
               .thenReturn("rich");
        Mockito.when(gifService.getGif(richTag))
               .thenReturn("gifUrl");
        mockMvc.perform(MockMvcRequestBuilders.get("/getcurrentgif/anyValue")
                                              .accept(MediaType.APPLICATION_JSON))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(MockMvcResultMatchers.model()
                                               .attribute("getTag", "rich"))
               .andExpect(MockMvcResultMatchers.model()
                                               .attribute("getGif", "gifUrl"));
    }

    @Test
    public void returnBrokeGif() throws Exception {
        Mockito.when(ratesService.compareValues("anyValue"))
                .thenReturn("broke");
        Mockito.when(gifService.getGif(brokeTag))
                .thenReturn("gifUrl");
        mockMvc.perform(MockMvcRequestBuilders.get("/getcurrentgif/anyValue")
                        .accept(MediaType.APPLICATION_JSON))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(MockMvcResultMatchers.model()
                                               .attribute("getTag", "broke"))
               .andExpect(MockMvcResultMatchers.model()
                                               .attribute("getGif", "gifUrl"));
    }

    @Test
    public void returnZeroGif() throws Exception {
        Mockito.when(ratesService.compareValues("anyValue"))
                .thenReturn("zero");
        Mockito.when(gifService.getGif(zeroTag))
                .thenReturn("gifUrl");
        mockMvc.perform(MockMvcRequestBuilders.get("/getcurrentgif/anyValue")
                        .accept(MediaType.APPLICATION_JSON))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(MockMvcResultMatchers.model()
                                               .attribute("getTag", "zero"))
               .andExpect(MockMvcResultMatchers.model()
                                               .attribute("getGif", "gifUrl"));
    }

    @Test
    public void returnMistakeGif() throws Exception {
        Mockito.when(ratesService.compareValues("anyValue"))
                .thenReturn("mistake");
        Mockito.when(gifService.getGif(mistakeTag))
                .thenReturn("gifUrl");
        mockMvc.perform(MockMvcRequestBuilders.get("/getcurrentgif/anyValue")
                        .accept(MediaType.APPLICATION_JSON))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(MockMvcResultMatchers.model()
                                               .attribute("getTag", "mistake"))
               .andExpect(MockMvcResultMatchers.model()
                                               .attribute("getGif", "gifUrl"));
    }
}
