package ru.fastfood.service;

import ru.fastfood.model.Order;
import ru.fastfood.model.OrderStatus;

import java.util.List;

public interface OrderService {

    Order save(Order order);

    void deleteById(int id);

    Order findById(int id);

    OrderStatus getStatusById(int id);

    void setStatusById(int id, OrderStatus status);

    List<Order> findAll();
}
