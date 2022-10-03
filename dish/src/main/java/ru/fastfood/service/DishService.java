package ru.fastfood.service;

import ru.fastfood.model.Dish;

import java.util.List;

public interface DishService {

    void add(Dish dish);

    void update(Dish dish);

    void deleteByName(String dishName);

    Dish findByName(String dishName);

    List<Dish> findALL();
}
