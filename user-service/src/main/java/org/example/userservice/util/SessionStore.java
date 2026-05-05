package org.example.userservice.util;

import org.example.userservice.entity.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionStore {

    private static final Map<String, User> sessions = new HashMap<>();

    public static String createSession(User user) {
        String sessionId = UUID.randomUUID().toString();
        sessions.put(sessionId, user);
        return sessionId;
    }

    public static User getUser(String sessionId) {
        return sessions.get(sessionId);
    }

    public static void remove(String sessionId) {
        sessions.remove(sessionId);
    }
}
