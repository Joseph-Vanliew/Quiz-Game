package com.kenzie.app;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class CluesHttpClient {
    //sending get request to api
    public static String sendGET(String url) {

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create(url);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .GET()
                .build();
        try {
            HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = httpResponse.statusCode();
            if (statusCode == 200) {
                return httpResponse.body();
            } else {
                return String.format("GET request failed: %d status code received", statusCode);
            }
        } catch (IOException | InterruptedException e) {
            return e.getMessage();
        }
    }

    //create list of clues from response body
    public static List<Clue> getCluesList(String httpResponseBody) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        CluesListDTO clueDTO = objectMapper.readValue(httpResponseBody, CluesListDTO.class);
        List<Clue> listOfClues = clueDTO.getClues();
        return listOfClues;
    }

    //Generate 10 random indexes from list of clues
    public static List<Clue> generateRandomIndicesFromList(List<Clue> listOfClues) throws JsonProcessingException {
        Random r = new Random();
        List<Clue> clueIndices = new ArrayList<>();
        int getRandomIndex = r.nextInt(listOfClues.size()) + 1;
        Clue clue;

        while (clueIndices.size() < 10) {
           clue =  listOfClues.get(getRandomIndex);
           if(!(clueIndices.contains(clue))) {
               clueIndices.add(clue);
            }
            getRandomIndex = r.nextInt(listOfClues.size()) + 1;
        }
        return clueIndices;
    }
}



