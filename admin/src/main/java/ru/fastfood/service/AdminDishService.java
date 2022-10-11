package ru.fastfood.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.fastfood.api.DishAPI;
import ru.fastfood.dto.DishDTO;

import java.util.List;

@Service
public class AdminDishService {

    private static final Logger LOG = LoggerFactory.getLogger(AdminDishService.class);

    private final RestTemplate restTemplate;

    public AdminDishService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void add(DishDTO dish) {
        try {
            restTemplate.postForObject(DishAPI.DISH, dish, DishDTO.class);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    public void update(DishDTO dish) {
        try {
            restTemplate.patchForObject(DishAPI.DISH, dish, DishDTO.class);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    public void deleteById(int id) {
        try {
            restTemplate.delete(DishAPI.DISH_ID, id);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    public DishDTO findById(int id) {
        DishDTO dishDTO = null;
        try {
            dishDTO = restTemplate.getForObject(DishAPI.DISH_ID, DishDTO.class, id);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return dishDTO;
    }

    public List<DishDTO> findALL() {
        DishDTO[] dishes = restTemplate.getForEntity(DishAPI.DISH, DishDTO[].class).getBody();
        if (dishes == null || dishes.length == 0) {
            return List.of();
        }
        return List.of(dishes);
    }
}
