package ru.fastfood.service;

import org.springframework.stereotype.Service;
import ru.fastfood.model.Dish;
import ru.fastfood.repository.DishRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DishDatabaseService implements DishService {

    private final DishRepository dishRepository;

    public DishDatabaseService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @Override
    public Dish add(Dish dish) {
        if (dishRepository.findDishByName(dish.getName()).isPresent()) {
            throw new IllegalArgumentException("Add error. Dish already exists");
        }
       return dishRepository.save(dish);
    }

    @Override
    public Dish update(Dish dish) {
        var rzl = dishRepository.findDishByName(dish.getName())
                .orElseThrow(() -> new NoSuchElementException("Update error. Dish not found"));
        rzl.setIngredients(dish.getIngredients());
        rzl.setPrice(dish.getPrice());
       return dishRepository.save(rzl);
    }

    @Override
    public void deleteById(int id) {
        findDish(id);
        dishRepository.deleteById(id);
    }

    @Override
    public Dish findById(int id) {
        return findDish(id);
    }

    @Override
    public List<Dish> findALL() {
        return (List<Dish>) dishRepository.findAll();
    }

    private Dish findDish(int id) {
        return dishRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Id error. Dish not found"));
    }
}
