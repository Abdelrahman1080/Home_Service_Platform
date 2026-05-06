package org.example.servicecatalogservice.service;

import org.example.servicecatalogservice.dto.AuthResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
@Service
public class UserServiceClient {

    private final RestTemplate restTemplate;

    public UserServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public AuthResponse validate(String sessionId) {
        String url = "http://localhost:8080/user-service-1.0-SNAPSHOT/api/auth/validate";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-SESSION-ID", sessionId);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<AuthResponse> response =
                restTemplate.exchange(url, HttpMethod.GET, entity, AuthResponse.class);

        return response.getBody();
    }
}
