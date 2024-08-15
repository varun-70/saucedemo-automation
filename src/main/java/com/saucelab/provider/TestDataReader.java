package com.saucelab.provider;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saucelab.constants.Constants;

import java.io.File;
import java.io.IOException;

public class TestDataReader {
    public void readFromJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(new File("./src/main/resources/testData/test_data.json"));
            Constants.setURL(jsonNode.get("paths").get("url").asText());
            Constants.setPassword(jsonNode.get("login").get(0).get("password").asText());
            Constants.setStandardUser(jsonNode.get("login").get(0).get("username").asText());
            Constants.setLockedOutUser(jsonNode.get("login").get(1).get("username").asText());
            Constants.setProblemUser(jsonNode.get("login").get(2).get("username").asText());
            Constants.setPerformanceGlitchUser(jsonNode.get("login").get(3).get("username").asText());
            Constants.setErrorUser(jsonNode.get("login").get(4).get("username").asText());
            Constants.setVisualUser(jsonNode.get("login").get(5).get("username").asText());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


