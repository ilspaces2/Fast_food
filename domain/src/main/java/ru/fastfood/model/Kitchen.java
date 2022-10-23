package ru.fastfood.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "kitchen")
public class Kitchen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int orderId;

    @Enumerated(EnumType.STRING)
    private Status status;
}
