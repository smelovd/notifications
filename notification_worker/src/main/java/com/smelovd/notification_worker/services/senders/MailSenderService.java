package com.smelovd.notification_worker.services.senders;

import com.smelovd.notification_worker.entity.NotificationStatus;
import com.smelovd.notification_worker.services.senders.senders_metadata.Sender;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService implements Sender {

    @Override
    public NotificationStatus send(String serviceUserId, String message) {
        System.out.println("MAIL \" + message + \" sent to " + serviceUserId);
        return NotificationStatus.DONE;
    }
}
