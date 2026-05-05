package org.example.userservice.util;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.HttpHeaders;
import org.example.userservice.entity.User;
import org.example.userservice.enums.Role;

@ApplicationScoped
public class AuthUtil {

    public User getUser(HttpHeaders headers) {
        String sessionId = headers.getHeaderString("X-SESSION-ID");
        if (sessionId == null) return null;

        return SessionStore.getUser(sessionId);
    }

    public boolean isAdmin(User user) {
        return user != null && user.getRole() == Role.ADMIN;
    }

    public boolean isCustomer(User user) {
        return user != null && user.getRole() == Role.CUSTOMER;
    }

    public boolean isProvider(User user) {
        return user != null && user.getRole() == Role.PROVIDER;
    }
}