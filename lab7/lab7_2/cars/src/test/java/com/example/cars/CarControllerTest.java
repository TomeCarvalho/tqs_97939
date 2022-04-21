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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


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
                .andExpect(jsonPath("$.maker", is(mustang.getMaker())))
                .andExpect(jsonPath("$.model", is(mustang.getModel())));
        verify(service, times(1)).save(Mockito.any());
    }

    @Test
    void givenManyCars_whenGetCars_thenReturnJsonArray() throws Exception {
        Car mustang = new Car("Ford", "Mustang");
        Car corvette = new Car("Chevrolet", "Corvette");
        Car accord = new Car("Honda", "Accord");
        List<Car> cars = Arrays.asList(mustang, corvette, accord);
        when(service.getAllCars()).thenReturn(cars);
        mvc.perform(get("/api/cars").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].maker", is(mustang.getMaker())))
                .andExpect(jsonPath("$[0].model", is(mustang.getModel())))
                .andExpect(jsonPath("$[1].maker", is(corvette.getMaker())))
                .andExpect(jsonPath("$[1].model", is(corvette.getModel())))
                .andExpect(jsonPath("$[2].maker", is(accord.getMaker())))
                .andExpect(jsonPath("$[2].model", is(accord.getModel())));
        verify(service, times(1)).getAllCars();
    }

    @Test
    void testGetCarById() throws Exception {
        Car mustang = new Car("Ford", "Mustang");
        when(service.getCarDetails(mustang.getCarId())).thenReturn(Optional.of(mustang));
        mvc.perform(get("/api/cars/" + mustang.getCarId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.maker", is(mustang.getMaker())))
                .andExpect(jsonPath("$.model", is(mustang.getModel())));
        verify(service, times(1)).getCarDetails(mustang.getCarId());
    }
}
