package ru.fastfood.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id")
public class DishDTO {

    private int id;

    private String name;

    private String ingredients;

    private int price;
}
