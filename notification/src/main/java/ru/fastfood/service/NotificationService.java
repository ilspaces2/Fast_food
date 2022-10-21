package ru.fastfood.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
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

    @KafkaListener(topics = "messengers")
    public void listenAndSaveMessage(ConsumerRecord<Integer, Notification> input) {
        save(input.value());
    }
}
