package ru.fastfood.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.NoSuchElementException;

@ControllerAdvice
public class IllegalArgAndNoSuchElementExceptionsHandler {

    private final ObjectMapper objectMapper;

    public IllegalArgAndNoSuchElementExceptionsHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @ExceptionHandler(value = {IllegalArgumentException.class, NoSuchElementException.class})
    public void handleException(Exception e, HttpServletResponse response) throws IOException {
        response.setStatus("IllegalArgumentException".equals(e.getClass().getSimpleName())
                ? HttpStatus.CONFLICT.value() : HttpStatus.NOT_FOUND.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() {
            {
                put("message", e.getMessage());
            }
        }));
    }
}
