package com.shaheen.csnotification.controller;

import com.shaheen.csnotification.openapi.api.MultiCastNotificationApi;
import com.shaheen.csnotification.openapi.api.NotificationsApi;
import com.shaheen.csnotification.openapi.model.MultiCastNotificationRequest;
import com.shaheen.csnotification.openapi.model.NotificationRequest;
import com.shaheen.csnotification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.List;
import java.util.Optional;

@RestController
public class NotificationController implements NotificationsApi, MultiCastNotificationApi {
  private final NotificationService notificationService;

  @Autowired
  public NotificationController(NotificationService notificationService) {
    this.notificationService = notificationService;
  }

  @Override
  public ResponseEntity<Void> notifySubscribersWithSingleMessage(
      MultiCastNotificationRequest multiCastNotificationRequest) {
    notificationService.notifySubscribersWithSingleMessage(multiCastNotificationRequest);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @Override
  public Optional<NativeWebRequest> getRequest() {
    return NotificationsApi.super.getRequest();
  }

  @Override
  public ResponseEntity<Void> notifyAllSubscribers(List<NotificationRequest> notificationRequest) {
    notificationService.notifyAllSubscribers(notificationRequest);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
