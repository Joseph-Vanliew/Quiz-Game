package com.kenzie.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CustomHttpClientTest {
    private MockWebServer mockWebServer;

    private static String GET_CLUES_RESPONSE;
    private static final String INPUT_FILE = "clues.json";
    private static String URL_ALL = "api/clues";

    @BeforeEach
    void init() throws IOException {
        this.mockWebServer = new MockWebServer();
        this.mockWebServer.start();

        Path filePath = Path.of(INPUT_FILE);
        GET_CLUES_RESPONSE = Files.readString(filePath);

    }

    @Test
    public void canSendGET() {
        try{
            this.mockWebServer.enqueue(new MockResponse()
                    .addHeader("Content-Type", "application/json")
                    .setBody(GET_CLUES_RESPONSE)
                    .setResponseCode(200));

            CustomHttpClient restClient = new CustomHttpClient();

            String result = restClient.sendGET(this.mockWebServer.url(URL_ALL).toString());

            assertEquals(GET_CLUES_RESPONSE, result);
        }
        catch (Exception e){
            fail(e.getMessage());
        }
    }
}
