package com.shaheen.csnotification.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

@Configuration
@Slf4j
public class AppConfig {
  @Value("${com.shaheen.fireBase.appName}")
  private String fireBaseAppName;

  @Value("${com.shaheen.fireBase.googleCredentials}")
  private String encodedGoogleCredentials;

  @Bean
  public FirebaseMessaging firebaseMessaging() throws IOException {
    byte[] decoded = Base64.getDecoder().decode(encodedGoogleCredentials);
    InputStream targetStream = new ByteArrayInputStream(decoded);
    GoogleCredentials googleCredentials = GoogleCredentials.fromStream(targetStream);
    FirebaseOptions firebaseOptions =
        FirebaseOptions.builder().setCredentials(googleCredentials).build();
    FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions, fireBaseAppName);
    return FirebaseMessaging.getInstance(app);
  }
}
