package ru.fastfood.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.fastfood.dto.DishDTO;
import ru.fastfood.service.AdminDishService;

@Controller
public class AdminDishController {

    private final AdminDishService dishService;

    public AdminDishController(AdminDishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("dishes", dishService.findALL());
        return "index";
    }

    @GetMapping("/formAddDish")
    public String formAddDish() {
        return "formAddDish";
    }

    @PostMapping("/addDish")
    public String save(@ModelAttribute DishDTO dish) {
        dishService.add(dish);
        return "redirect:/index";
    }

    @GetMapping("/formUpdateDish/{id}")
    public String formUpdateDish(Model model, @PathVariable int id) {
        model.addAttribute("dish", dishService.findById(id));
        return "formUpdateDish";
    }

    @PostMapping("/updateDish")
    public String update(@ModelAttribute DishDTO dish) {
        dishService.update(dish);
        return "redirect:/index";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable int id) {
        dishService.deleteById(id);
        return "redirect:/index";
    }
}
