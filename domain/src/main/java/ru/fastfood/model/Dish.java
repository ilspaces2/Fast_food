package ru.fastfood.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(of = "name")
public class Dish {

    private String name;

    private String ingredients;

    private int price;
}
