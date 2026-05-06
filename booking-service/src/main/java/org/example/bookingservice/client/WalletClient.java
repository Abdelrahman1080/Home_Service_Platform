package org.example.bookingservice.client;

import org.example.bookingservice.dto.WalletResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class WalletClient {

    private final RestTemplate restTemplate;

    public WalletClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /*public boolean deduct(Long userId, Double amount) {

        String url = "http://localhost:8080/user-service-1.0-SNAPSHOT/api/customer/wallet/deduct";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String body = """
        {
          "userId": %d,
          "amount": %f
        }
        """.formatted(userId, amount);

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response =
                restTemplate.postForEntity(url, entity, String.class);

        return response.getStatusCode().is2xxSuccessful();
    }*/

    public boolean deduct(Long userId, Double amount) {

        String url = "http://localhost:8080/user-service-1.0-SNAPSHOT/api/customer/wallet/deduct";


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("userId", userId);
        body.put("amount", amount);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

       // WalletResponse response = restTemplate.postForObject(url, entity, WalletResponse.class);
        boolean response = restTemplate.postForObject(url, entity, Boolean.class);

        return response;
    }

    public void refund(Long userId, Double amount) {

        String url = "http://localhost:8080/user-service-1.0-SNAPSHOT/api/customer/wallet/refund";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String body = """
        {
          "userId": %d,
          "amount": %f
        }
        """.formatted(userId, amount);

        restTemplate.postForEntity(url, new HttpEntity<>(body, headers), String.class);
    }
}
