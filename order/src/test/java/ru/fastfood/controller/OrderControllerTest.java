package ru.fastfood.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.fastfood.model.Order;
import ru.fastfood.model.Status;
import ru.fastfood.service.OrderDataBaseService;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderDataBaseService orderDataBaseService;

    @Test
    public void whenAddOrder() throws Exception {
        Order orderInput = new Order();
        orderInput.setId(1);
        orderInput.setUserEmail("email");
        Order orderOutput = new Order();
        orderOutput.setId(1);
        orderOutput.setUserEmail("email");
        orderOutput.setCreated(LocalDateTime.now());
        orderOutput.setStatus(Status.IN_WORK);
        when(orderDataBaseService.save(any(Order.class))).thenReturn(orderOutput);
        this.mockMvc.perform(post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper().writeValueAsString(orderInput))
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(mapper().writeValueAsString(orderOutput))));
    }


    @Test
    public void whenDeleteOrder() throws Exception {
        doNothing().when(orderDataBaseService).deleteById(any(Integer.class));
        this.mockMvc.perform(delete("/order/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Order deleted")));
    }

    @Test
    public void whenFindById() throws Exception {
        Order orderInput = new Order();
        orderInput.setId(1);
        orderInput.setUserEmail("email");
        when(orderDataBaseService.findById(any(Integer.class))).thenReturn(orderInput);
        this.mockMvc.perform(get("/order/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(mapper().writeValueAsString(orderInput))));
    }

    @Test
    public void whenFindAll() throws Exception {
        Order orderInput = new Order();
        orderInput.setId(1);
        orderInput.setUserEmail("email");
        Order orderInput2 = new Order();
        orderInput.setId(2);
        orderInput.setUserEmail("email2");
        Order orderInput3 = new Order();
        orderInput.setId(3);
        orderInput.setUserEmail("email3");
        List<Order> list = List.of(
                orderInput,
                orderInput2,
                orderInput3
        );
        when(orderDataBaseService.findAll()).thenReturn(list);
        this.mockMvc.perform(get("/order"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(mapper().writeValueAsString(list))));
    }

    @Test
    public void whenGetStatus() throws Exception {
        when(orderDataBaseService.getStatusById(any(Integer.class))).thenReturn(Status.COMPLETE);
        this.mockMvc.perform(get("/order/getStatus/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(mapper().writeValueAsString(Status.COMPLETE))));
    }

    @Test
    public void whenSetStatus() throws Exception {
        doNothing().when(orderDataBaseService).setStatusById(any(Integer.class), any(Status.class));
        this.mockMvc.perform(patch("/order/setStatus/{id}", 1)
                        .param("status", String.valueOf(Status.COMPLETE)))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Status changed")));
    }

    private ObjectMapper mapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        return mapper;
    }
}
