package ru.fastfood.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Order {

    private int id;

    private LocalDateTime created;

    private User user;

    private List<Dish> dishes;

    private OrderStatus status;
}
