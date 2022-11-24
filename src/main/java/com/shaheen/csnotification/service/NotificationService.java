package com.shaheen.csnotification.service;

import com.shaheen.csnotification.openapi.model.NotificationRequest;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public interface NotificationService {
  @Async
  void notifyAllSubscribers(List<NotificationRequest> notificationRequest);
}
