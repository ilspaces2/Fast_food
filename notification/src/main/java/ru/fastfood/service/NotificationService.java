package ru.fastfood.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.fastfood.model.Status;
import ru.fastfood.repository.NotificationRepository;
import ru.fastfood.model.Notification;

@Service
public class NotificationService {

    public final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    public void setStatusByItemIdFromService(int id, Status status) {
        notificationRepository.setStatusByItemIdFromService(id, status);
    }

    @KafkaListener(topics = "messengers")
    public void listenMessage(ConsumerRecord<Integer, Notification> input) {
        Notification notification = input.value();
        if (Status.NEW.equals(notification.getStatus())) {
            save(input.value());
        } else {
            setStatusByItemIdFromService(notification.getItemIdFromService(), notification.getStatus());
        }
    }
}
