package com.shaheen.csnotification.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.shaheen.csnotification.exception.GeneralServerError;
import com.shaheen.csnotification.openapi.model.NotificationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {
  private final FirebaseMessaging firebaseMessaging;
  private final ObjectMapper objectMapper;

  @Override
  public void notifyAllSubscribers(NotificationRequest notificationRequest) {
    Map data = objectMapper.convertValue(notificationRequest.getData(), Map.class);

    //    Notification notification =
    //        Notification.builder().setTitle(note.getSubject()).setBody(note.getContent()).build();

    Message message =
        Message.builder()
            .setToken(notificationRequest.getToken())
            //            .setNotification(notification)
            .putAllData(data)
            .build();
    try {
      firebaseMessaging.send(message);
    } catch (FirebaseMessagingException e) {
      log.error("Error Sending Message", e);
      throw new GeneralServerError("Failed to send notification");
    }
  }
}
