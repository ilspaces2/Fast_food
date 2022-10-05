package ru.fastfood.service;

import ru.fastfood.model.Dish;

import java.util.List;

public interface DishService {

    Dish add(Dish dish);

    Dish update(Dish dish);

    void deleteById(int id);

    Dish findById(int id);

    List<Dish> findALL();
}
