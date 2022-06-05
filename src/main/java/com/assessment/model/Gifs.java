package com.assessment.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Gifs {
    private Gifs data;
    private Images images;

    @Getter
    @Setter
    public static class Images {
        private Map<String, String> original;
    }
}
