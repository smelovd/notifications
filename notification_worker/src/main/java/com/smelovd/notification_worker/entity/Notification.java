package com.smelovd.notification_worker.entity;

import com.smelovd.notification_worker.services.senders.senders_metadata.SenderServiceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;


@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    private String id;
    private String fileId;
    private String serviceUserId;
    private SenderServiceType notificationService;
    private String notificationId;
    private Timestamp timestamp;
    private NotificationStatus status;
}
