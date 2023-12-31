package com.smelovd.notification_worker.kafka;

import com.smelovd.notification_api.entity.NotificationApi;
import com.smelovd.notification_worker.entity.Notification;
import com.smelovd.notification_worker.entity.NotificationStatus;
import com.smelovd.notification_worker.repositories.NotificationRepository;
import com.smelovd.notification_worker.repositories.NotificationRequestRepository;
import com.smelovd.notification_worker.services.NotificationService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.sql.Timestamp;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

    private final NotificationRepository notificationRepository;
    private final NotificationService notificationService;

    @KafkaListener(topics = "notifications", groupId = "1", batch = "true", concurrency = "2")
    public void notificationListener(List<Notification> notifications) {
        Flux.fromIterable(notifications)
                .doOnNext(n -> n.setStatus(notificationService.send(n)))
                .collectList()
                .doOnNext(System.out::println)
                .subscribe(notificationRepository::saveAll);
    }

    /*private final KafkaTemplate<String, NotificationApi> kafkaTemplate;
    private final NotificationRequestRepository notificationRequestRepository;

    @PostConstruct
    public void test() {
        for (int i = 0; i < 1000; i++) {
            *//*notificationRequestRepository.insert(NotificationRequestDTO.builder()
                            .id("1")
                            .message("Hello World!")
                            .status(NotificationRequestStatus.CREATED)
                    .build()).subscribe(System.out::println);*//*
            kafkaTemplate.send("notifications", NotificationApi.builder()
                            .id(String.valueOf(i))
                            .fileId(String.valueOf(i))
                            .notificationId("1")
                            .notificationService("SMS")
                            .serviceUserId(String.valueOf(i))
                            .status(NotificationStatus.CREATED)
                            .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build());
        }
    }*/

}
