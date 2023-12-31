package com.smelovd.notification_worker.services;

import com.smelovd.notification_worker.entity.Notification;
import com.smelovd.notification_worker.entity.NotificationStatus;
import com.smelovd.notification_worker.repositories.NotificationRequestRepository;
import com.smelovd.notification_worker.services.senders.MailSenderService;
import com.smelovd.notification_worker.services.senders.SmsSenderService;
import com.smelovd.notification_worker.services.senders.senders_metadata.Sender;
import com.smelovd.notification_worker.services.senders.senders_metadata.SenderServiceType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.Map;

import static com.smelovd.notification_worker.services.senders.senders_metadata.SenderServiceType.MAIL;
import static com.smelovd.notification_worker.services.senders.senders_metadata.SenderServiceType.SMS;

@Service
@Slf4j
public class NotificationService {

    private final Map<SenderServiceType, Sender> senderServiceMap;
    private final CachingService cachingService;

    @Autowired
    public NotificationService(MailSenderService mailSenderService, SmsSenderService smsSenderService, NotificationRequestRepository notificationRequestRepository, CachingService cachingService) {
        this.cachingService = cachingService;

        this.senderServiceMap = new EnumMap<>(SenderServiceType.class);
        senderServiceMap.put(SMS, smsSenderService);
        senderServiceMap.put(MAIL, mailSenderService);
    }

    public NotificationStatus send(Notification notification) {
        Sender sender = senderServiceMap.get(notification.getNotificationService());
        if (sender != null) {
            return sender.send(notification.getServiceUserId(),
                    cachingService.getMessageByRequestId(notification.getNotificationId()));
        }
        log.error("Invalid message type " + notification);
        return NotificationStatus.ERROR;
    }
}
