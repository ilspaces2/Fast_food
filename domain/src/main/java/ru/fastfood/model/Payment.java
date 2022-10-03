package ru.fastfood.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Payment {

    private int id;

    private LocalDateTime paymentTime;

    private int price;

    private boolean paid;
}
