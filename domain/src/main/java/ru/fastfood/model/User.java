package ru.fastfood.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(of = "email")
public class User {

    private String name;

    private String email;

    private String password;
}
