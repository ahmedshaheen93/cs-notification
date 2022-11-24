package com.shaheen.csnotification.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.shaheen.csnotification.exception.GeneralServerError;
import com.shaheen.csnotification.openapi.model.NotificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {
  private final FirebaseMessaging firebaseMessaging;
  private final ObjectMapper objectMapper;

  @Autowired
  public NotificationServiceImpl(FirebaseMessaging firebaseMessaging, ObjectMapper objectMapper) {
    this.firebaseMessaging = firebaseMessaging;
    this.objectMapper = objectMapper;
  }

  @Override
  public void notifyAllSubscribers(List<NotificationRequest> notificationRequests) {
    for (NotificationRequest notificationRequest : notificationRequests) {
      Map data = objectMapper.convertValue(notificationRequest.getData(), Map.class);
      Message message =
          Message.builder().setToken(notificationRequest.getToken()).putAllData(data).build();
      try {
        firebaseMessaging.send(message);
        log.info("Message sent successfully");
      } catch (FirebaseMessagingException e) {
        log.error("Error Sending Message", e);
        throw new GeneralServerError("Failed to send notification");
      }
    }
  }
}
