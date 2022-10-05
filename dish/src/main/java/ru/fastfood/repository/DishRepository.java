package ru.fastfood.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.fastfood.model.Dish;

import java.util.Optional;

@Repository
public interface DishRepository extends CrudRepository<Dish, Integer> {

    Optional<Dish> findDishByName(String dishName);
}
