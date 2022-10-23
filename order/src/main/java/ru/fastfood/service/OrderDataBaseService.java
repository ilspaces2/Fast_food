package ru.fastfood.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.fastfood.model.Notification;
import ru.fastfood.model.NotificationType;
import ru.fastfood.model.Order;
import ru.fastfood.model.Status;
import ru.fastfood.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderDataBaseService implements OrderService {

    private final OrderRepository orderRepository;

    private final KafkaTemplate<Integer, Notification> kafkaTemplate;

    public OrderDataBaseService(OrderRepository orderRepository, KafkaTemplate<Integer, Notification> kafkaTemplate) {
        this.orderRepository = orderRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public Order save(Order order) {
        order.setCreated(LocalDateTime.now());
        order.setStatus(Status.IN_WORK);
        orderRepository.save(order);
        sendToKafka(order.getId(), "messengers", Status.NEW);
        sendToKafka(order.getId(), "preorder", Status.NEW);
        return order;
    }

    @Override
    public void deleteById(int id) {
        findById(id);
        orderRepository.deleteById(id);
    }

    @Override
    public Order findById(int id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Order not found"));
    }

    @Override
    public Status getStatusById(int id) {
        return findById(id).getStatus();
    }

    @Override
    public void setStatusById(int id, Status status) {
        Arrays.stream(Status.values())
                .filter((st) -> st.equals(status))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Status incorrect"));
        findById(id);
        orderRepository.setStatusOrder(id, status);
    }

    @Override
    public List<Order> findAll() {
        return (List<Order>) orderRepository.findAll();
    }

    private void sendToKafka(int orderId, String topic, Status status) {
        Notification notification = new Notification();
        notification.setNotificationType(NotificationType.ORDER);
        notification.setItemIdFromService(orderId);
        notification.setStatus(status);
        kafkaTemplate.send(topic, notification);
    }

    @KafkaListener(topics = "cooked_order")
    public void listenKitchen(ConsumerRecord<Integer, Notification> input) {
        Notification notification = input.value();
        setStatusById(notification.getItemIdFromService(), notification.getStatus());
        sendToKafka(notification.getItemIdFromService(), "messengers", notification.getStatus());
    }
}
