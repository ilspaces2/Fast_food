package ru.fastfood.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fastfood.model.Order;
import ru.fastfood.model.OrderStatus;
import ru.fastfood.service.OrderService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("")
    public Order saveOrder(@RequestBody @Valid Order order) {
        return orderService.save(order);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable int id) {
        orderService.deleteById(id);
        return new ResponseEntity<>("Order deleted", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Order findById(@PathVariable int id) {
        return orderService.findById(id);
    }

    @GetMapping("")
    public List<Order> findAllOrders() {
        return orderService.findAll();
    }

    @GetMapping("/getStatus/{id}")
    public OrderStatus getOrderStatus(@PathVariable int id) {
        return orderService.getStatusById(id);
    }

    @PatchMapping("/setStatus/{id}")
    public ResponseEntity<String> setOrderStatus(@PathVariable int id, @RequestParam OrderStatus status) {
        orderService.setStatusById(id, status);
        return new ResponseEntity<>("Status changed", HttpStatus.OK);
    }
}
