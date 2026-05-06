package org.example.notificationservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.notificationservice.service.NotificationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService service;

    @GetMapping("/{userId}")
    public Object getUserNotifications(@PathVariable Long userId) {
        return service.getUserNotifications(userId);
    }
}
