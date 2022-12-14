package com.shaheen.csnotification.service;

import com.shaheen.csnotification.openapi.model.MultiCastNotificationRequest;
import com.shaheen.csnotification.openapi.model.NotificationRequest;

import java.util.List;

public interface NotificationService {
  void notifyAllSubscribers(List<NotificationRequest> notificationRequest);

  void notifySubscribersWithSingleMessage(
      MultiCastNotificationRequest multiCastNotificationRequest);
}
