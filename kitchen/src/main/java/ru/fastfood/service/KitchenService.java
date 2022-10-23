package ru.fastfood.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.fastfood.model.Kitchen;
import ru.fastfood.model.Notification;
import ru.fastfood.model.Status;
import ru.fastfood.repository.KitchenRepository;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

@Service
public class KitchenService {

    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    private final KafkaTemplate<Integer, Notification> kafkaTemplate;

    private final KitchenRepository kitchenRepository;

    public KitchenService(KafkaTemplate<Integer, Notification> kafkaTemplate, KitchenRepository kitchenRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.kitchenRepository = kitchenRepository;
    }

    public Kitchen save(Kitchen kitchen) {
        kitchen.setStatus(Status.IN_WORK);
        return kitchenRepository.save(kitchen);
    }

    private void setStatusByOrderId(int id, Status status) {
        kitchenRepository.setStatusByOrderId(id, status);
    }

    /**
     * Из уведомления берем id заказа и сохраняем его в бд.
     * Далее готовим.
     *
     * @param input входящее уведомление от сервиса Order.
     */
    @KafkaListener(topics = "preorder")
    public void listenAndSaveMessage(ConsumerRecord<Integer, Notification> input) {
        Notification notification = input.value();
        Kitchen kitchen = new Kitchen();
        kitchen.setOrderId(notification.getItemIdFromService());
        cooking(
                save(kitchen),
                notification,
                (num) -> random.nextInt(5) == 3);
    }

    /**
     * Метод в зависимости от condition выполнит заказ или нет
     * и отправит информацию с результатом в сервис Order.
     *
     * @param kitchen      модель с id заказа и статусом.
     * @param notification входящее уведомление.
     * @param condition    условие при котором будут готовить или нет.
     */
    private void cooking(Kitchen kitchen, Notification notification, Predicate<Boolean> condition) {
        try {
            if (condition.test(true)) {
                Thread.sleep(10_000);
                sendMessage("cooked_order", kitchen, notification, Status.CANCELED);
            } else {
                Thread.sleep(60_000);
                sendMessage("cooked_order", kitchen, notification, Status.COMPLETE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Меняем статус китчена в базе данных.
     * Меняем статус уведомления.
     * Оправляем сообщение в другой сервис.
     *
     * @param topic        имя топика.
     * @param kitchen      модель с id заказа и статусом.
     * @param notification входящее уведомление.
     * @param status       статус.
     */

    private void sendMessage(String topic, Kitchen kitchen, Notification notification, Status status) {
        setStatusByOrderId(kitchen.getOrderId(), status);
        notification.setStatus(status);
        kafkaTemplate.send(topic, notification);
    }
}
