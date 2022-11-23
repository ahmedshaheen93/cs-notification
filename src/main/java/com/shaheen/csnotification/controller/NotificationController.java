package com.shaheen.csnotification.controller;

import com.shaheen.csnotification.openapi.api.NotificationsApi;
import com.shaheen.csnotification.openapi.model.NotificationRequest;
import com.shaheen.csnotification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NotificationController implements NotificationsApi {
  private final NotificationService notificationService;

  @Autowired
  public NotificationController(NotificationService notificationService) {
    this.notificationService = notificationService;
  }

  @Override
  public ResponseEntity<Void> notifyAllSubscribers(List<NotificationRequest> notificationRequest) {
    notificationService.notifyAllSubscribers(notificationRequest);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
