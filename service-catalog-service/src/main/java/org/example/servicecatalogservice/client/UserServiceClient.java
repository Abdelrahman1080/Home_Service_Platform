/*package org.example.servicecatalogservice.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserServiceClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public Object validateToken(String token) {
        return restTemplate.getForObject(
                "http://localhost:8080/auth/validate?token=" + token,
                Object.class
        );
    }
}
*/