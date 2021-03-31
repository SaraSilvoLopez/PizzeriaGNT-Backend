package com.example.demo.infraestructure;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest.BodyPublishers;

import com.example.demo.dto.pizzadtos.ImageDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Repository;

@Repository
public class ImageRepositoryImp implements ImageRepository {
    public String create(ImageDTO dto) throws IOException, InterruptedException{
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(dto);
        HttpRequest request = HttpRequest
            .newBuilder()
            .uri(URI.create("http://localhost:8082/save"))
            .header("Content-Type", "application/json")
            .POST(BodyPublishers.ofString(json))
            .build();

        HttpClient httpClient = HttpClient
            .newBuilder()
            .version(Version.HTTP_1_1)
            .build();
            
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        JsonNode node = mapper.readTree(response.body());
        String public_id = node.get("public_id").asText();
        return public_id;
    }
}
