package ru.fastfood.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.fastfood.model.Notification;
import ru.fastfood.model.NotificationType;
import ru.fastfood.model.Order;
import ru.fastfood.model.OrderStatus;
import ru.fastfood.repository.OrderRepository;

import java.time.LocalDateTime;
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
        order.setStatus(OrderStatus.IN_WORK);
        orderRepository.save(order);
        sendToKafka(order, "New order");
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
    public OrderStatus getStatusById(int id) {
        return findById(id).getStatus();
    }

    @Override
    public void setStatusById(int id, OrderStatus status) {
        if (!OrderStatus.IN_WORK.equals(status) && !OrderStatus.DELIVERING.equals(status)
                && !OrderStatus.COMPLETE.equals(status)) {
            throw new IllegalArgumentException("Status incorrect");
        }
        findById(id);
        orderRepository.setStatusOrder(id, status);
    }

    @Override
    public List<Order> findAll() {
        return (List<Order>) orderRepository.findAll();
    }

    private void sendToKafka(Order order, String message) {
        Notification notification = new Notification();
        notification.setNotificationType(NotificationType.ORDER);
        notification.setItemIdFromService(order.getId());
        notification.setMessageText(message);
        kafkaTemplate.send("messengers", notification);
    }
}
