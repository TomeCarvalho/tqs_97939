package com.example.cars;

import com.example.cars.controller.CarController;
import com.example.cars.model.Car;
import com.example.cars.service.CarManagerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarController.class)
public class CarControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarManagerService service;

    @BeforeEach
    public void setUp() throws Exception {
    }

    @Test
    void whenPostCar_thenCreateCar() throws Exception {
        Car mustang = new Car("Ford", "Mustang");
        when(service.save(Mockito.any())).thenReturn(mustang);
        mvc.perform(
                        post("/api/cars")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonUtils.toJson(mustang)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.maker", is("Ford")));
        verify(service, times(1)).save(Mockito.any());
    }

    @Test
    void givenManyCars_whenGetCars_thenReturnJsonArray() throws Exception {
        Car mustang = new Car("Ford", "Mustang");
        Car corvette = new Car("Chevrolet", "Corvette");
        Car accord = new Car("Honda", "Accord");
    }

    @Test
    void testGetCarById() throws Exception {
        Car mustang = new Car("Ford", "Mustang");
    }
}
