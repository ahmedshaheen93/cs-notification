package com.shaheen.csnotification.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.*;
import com.shaheen.csnotification.exception.NotificationException;
import com.shaheen.csnotification.openapi.model.MultiCastNotificationRequest;
import com.shaheen.csnotification.openapi.model.NotificationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
  private final FirebaseMessaging firebaseMessaging;
  private final ObjectMapper objectMapper;

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
        throw new NotificationException(
            "Failed to send notification", HttpStatus.valueOf(e.getHttpResponse().getStatusCode()));
      }
    }
  }

  @Override
  public void notifySubscribersWithSingleMessage(
      MultiCastNotificationRequest multiCastNotificationRequest) {
    List<String> registrationTokens = multiCastNotificationRequest.getTokens();
    Map data = objectMapper.convertValue(multiCastNotificationRequest.getData(), Map.class);

    MulticastMessage message =
        MulticastMessage.builder().putAllData(data).addAllTokens(registrationTokens).build();
    try {
      BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);
      if (response.getFailureCount() > 0) {
        List<SendResponse> responses = response.getResponses();
        List<String> failedTokens = new ArrayList<>();
        for (int i = 0; i < responses.size(); i++) {
          if (!responses.get(i).isSuccessful()) {
            // The order of responses corresponds to the order of the registration tokens.
            failedTokens.add("" + i);
          }
        }
        if (!CollectionUtils.isEmpty(failedTokens)) {
          throw new NotificationException(
              String.format("Failed to send notification for tokens[%s]", failedTokens.toArray()),
              HttpStatus.MULTI_STATUS);
        }
      }

    } catch (FirebaseMessagingException e) {
      log.error("Error Sending Message", e);
      throw new NotificationException(
          "Failed to send notification", HttpStatus.valueOf(e.getHttpResponse().getStatusCode()));
    }
  }
}
