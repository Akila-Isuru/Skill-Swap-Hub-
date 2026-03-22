package org.example.backend.controller;

import org.example.backend.dto.NotificationDTO;
import org.example.backend.service.NotificationService;
import org.example.backend.utill.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<APIResponse<List<NotificationDTO>>> getNotifications(@PathVariable Long userId) {
        return new ResponseEntity<>(new APIResponse<>(200, "Success", notificationService.getNotificationsForUser(userId)), HttpStatus.OK);
    }

    @PutMapping("/mark-as-read/{id}")
    public ResponseEntity<APIResponse<String>> markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return new ResponseEntity<>(new APIResponse<>(200, "Notification marked as read", null), HttpStatus.OK);
    }
}