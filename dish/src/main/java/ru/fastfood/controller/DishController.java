package ru.fastfood.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import ru.fastfood.model.Dish;
import ru.fastfood.service.DishService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/dish")
public class DishController {

    private final DishService dishService;


    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @PostMapping("")
    public Dish save(@RequestBody @Valid Dish dish) {
        return dishService.add(dish);
    }

    @PatchMapping("")
    public Dish update(@RequestBody @Valid Dish dish) {
        return dishService.update(dish);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable int id) {
        dishService.deleteById(id);
        return new ResponseEntity<>("Dish deleted", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Dish findBYId(@PathVariable int id) {
        return dishService.findById(id);
    }

    @GetMapping("")
    public List<Dish> findAll() {
        return dishService.findALL();
    }
}
