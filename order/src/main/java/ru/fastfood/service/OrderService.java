package ru.fastfood.service;

import ru.fastfood.model.Order;
import ru.fastfood.model.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    void save(Order order);

    void deleteById(int id);

    Optional<Order> findById(int id);

    OrderStatus getStatusById(int id);

    List<Order> findAll();
}
