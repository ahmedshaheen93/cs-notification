package com.shaheen.csnotification.controller;

import com.shaheen.csnotification.openapi.api.NotificationsApi;
import com.shaheen.csnotification.openapi.model.NotificationRequest;
import com.shaheen.csnotification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NotificationController implements NotificationsApi {
  private final NotificationService notificationService;

  @Override
  public ResponseEntity<Void> notifyAllSubscribers(NotificationRequest notificationRequest) {
    notificationService.notifyAllSubscribers(notificationRequest);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
