package ru.fastfood.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Delivery {

    private int id;

    private LocalDateTime deliveryTime;

    private Payment payment;

    private Order order;

    private String address;

    private String comment;

    private boolean delivered;

}
