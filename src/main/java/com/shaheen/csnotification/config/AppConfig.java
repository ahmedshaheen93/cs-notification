package com.shaheen.csnotification.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
@Slf4j
public class AppConfig {
  @Bean
  public FirebaseMessaging firebaseMessaging() throws IOException {
    FileInputStream serviceAccount = new FileInputStream("delivery-app-account.json");
    GoogleCredentials googleCredentials = GoogleCredentials.fromStream(serviceAccount);
    FirebaseOptions firebaseOptions =
        FirebaseOptions.builder().setCredentials(googleCredentials).build();
    FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions, "delivery-app");
    return FirebaseMessaging.getInstance(app);
  }
}
