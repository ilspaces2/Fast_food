package ru.fastfood.controller;

import org.springframework.web.bind.annotation.*;
import ru.fastfood.model.Order;
import ru.fastfood.model.OrderStatus;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @PostMapping("")
    void saveOrder() {
    }

    @DeleteMapping("/{id}")
    void deleteOrder(@PathVariable int id) {
    }

    @GetMapping("/{id}")
    Order findOrder(@PathVariable int id) {
        return null;
    }

    @GetMapping("")
    List<Order> findAllOrders() {
        return null;
    }

    @GetMapping("/status/{id}")
    OrderStatus getOrderStatus(@PathVariable int id) {
        return null;
    }
}
