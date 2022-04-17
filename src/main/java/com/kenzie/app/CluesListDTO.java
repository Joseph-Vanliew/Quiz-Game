package com.kenzie.app;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class CluesListDTO {
    @JsonProperty("clues")
    private List<Clue> clues;

    public List<Clue> getClues() {
        return clues;
    }

}
