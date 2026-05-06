package org.example.bookingservice.client;

import lombok.Data;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    @Data
    public static class AuthResponse {
        private Long userId;
        private String role;
        private String username;
    }
}
