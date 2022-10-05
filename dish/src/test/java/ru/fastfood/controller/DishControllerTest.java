package ru.fastfood.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.fastfood.model.Dish;
import ru.fastfood.service.DishDatabaseService;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DishControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DishDatabaseService dishDatabaseService;

    @Test
    public void whenAddDish() throws Exception {
        Dish dish = new Dish(1, "dish", "some ingredients", 15);
        ObjectMapper mapper = new ObjectMapper();
        when(dishDatabaseService.add(any())).thenReturn(dish);
        this.mockMvc.perform(post("/dish")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dish))
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(mapper.writeValueAsString(dish))));
    }

    @Test
    public void whenUpdateDish() throws Exception {
        Dish dish = new Dish(1, "dish", "some ingredients", 15);
        ObjectMapper mapper = new ObjectMapper();
        when(dishDatabaseService.update(any())).thenReturn(dish);
        this.mockMvc.perform(patch("/dish")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dish))
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(mapper.writeValueAsString(dish))));
    }

    @Test
    public void whenDeleteDish() throws Exception {
        doNothing().when(dishDatabaseService).deleteById(any(Integer.class));
        this.mockMvc.perform(delete("/dish/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Dish deleted")));
    }

    @Test
    public void whenGetOneDish() throws Exception {
        Dish dish = new Dish(1, "dish", "some ingredients", 15);
        when(dishDatabaseService.findById(any(Integer.class))).thenReturn(dish);
        this.mockMvc.perform(get("/dish/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(new ObjectMapper().writeValueAsString(dish))));
    }

    @Test
    public void whenGetListDishes() throws Exception {
        List<Dish> list = List.of(
                new Dish(1, "dish1", "some ingredients1", 15),
                new Dish(2, "dish2", "some ingredients2", 20),
                new Dish(3, "dish3", "some ingredients3", 25)
        );
        when(dishDatabaseService.findALL()).thenReturn(list);
        this.mockMvc.perform(get("/dish"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(new ObjectMapper().writeValueAsString(list))));
    }
}
