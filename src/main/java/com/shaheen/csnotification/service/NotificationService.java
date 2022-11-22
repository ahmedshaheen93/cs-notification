package com.shaheen.csnotification.service;

import com.shaheen.csnotification.openapi.model.NotificationRequest;

public interface NotificationService {
  void notifyAllSubscribers(NotificationRequest notificationRequest);
}
