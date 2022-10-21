package ru.fastfood.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;


    private int itemIdFromService;

    private String messageText;

}
