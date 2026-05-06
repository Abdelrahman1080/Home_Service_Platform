package org.example.notificationservice.service;

import lombok.RequiredArgsConstructor;
import org.example.notificationservice.entity.Notification;
import org.example.notificationservice.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository repo;

    public void notifyUser(Long userId, String message) {

        // console (simulation SMS)
        System.out.println("📩 Notification to user " + userId + ": " + message);

        Notification notification = Notification.builder()
                .userId(userId)
                .message(message)
                .createdAt(LocalDateTime.now())
                .build();

        repo.save(notification);
    }

    public List<Notification> getUserNotifications(Long userId) {
        return repo.findByUserId(userId);
    }
}
