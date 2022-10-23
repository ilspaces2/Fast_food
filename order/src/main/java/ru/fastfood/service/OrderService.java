package ru.fastfood.service;

import ru.fastfood.model.Order;
import ru.fastfood.model.Status;

import java.util.List;

public interface OrderService {

    Order save(Order order);

    void deleteById(int id);

    Order findById(int id);

    Status getStatusById(int id);

    void setStatusById(int id, Status status);

    List<Order> findAll();
}
