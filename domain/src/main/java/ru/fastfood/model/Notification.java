package ru.fastfood.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(of = "email")
public class Notification {

    private String text;

    private String email;
}
