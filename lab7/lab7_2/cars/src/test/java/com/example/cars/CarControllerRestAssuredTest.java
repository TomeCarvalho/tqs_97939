package com.example.cars;

import com.example.cars.controller.CarController;
import com.example.cars.model.Car;
import com.example.cars.service.CarManagerService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
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
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CarController.class)
class CarControllerRestAssuredTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarManagerService service;

    @BeforeEach
    public void setUp() throws Exception {
        RestAssuredMockMvc.mockMvc(mvc);
    }

    @Test
    void whenPostCar_thenCreateCar() throws Exception {
        Car mustang = new Car("Ford", "Mustang");
        when(service.save(Mockito.any())).thenReturn(mustang);
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .body(JsonUtils.toJson(mustang))
                .when()
                .post("/api/cars")
                .then()
                .statusCode(201)
                .and().body("maker", equalTo(mustang.getMaker()))
                .and().body("model", equalTo(mustang.getModel()))
        ;
        verify(service, times(1)).save(Mockito.any());
    }

    @Test
    void givenManyCars_whenGetCars_thenReturnJsonArray() {
        Car mustang = new Car("Ford", "Mustang");
        Car corvette = new Car("Chevrolet", "Corvette");
        Car accord = new Car("Honda", "Accord");
        List<Car> cars = Arrays.asList(mustang, corvette, accord);
        when(service.getAllCars()).thenReturn(cars);

        RestAssuredMockMvc.given()
                .contentType("application/json")
                .get("/api/cars")
                .then()
                .statusCode(200)
                .body("", hasSize(3))
                .and().body("[0].maker", equalTo(mustang.getMaker()))
                .and().body("[0].model", equalTo(mustang.getModel()))
                .and().body("[1].maker", equalTo(corvette.getMaker()))
                .and().body("[1].model", equalTo(corvette.getModel()))
                .and().body("[2].maker", equalTo(accord.getMaker()))
                .and().body("[2].model", equalTo(accord.getModel()))
        ;
        verify(service, times(1)).getAllCars();
    }

    @Test
    void testGetCarById() {
        Car mustang = new Car("Ford", "Mustang");
        when(service.getCarDetails(mustang.getCarId())).thenReturn(Optional.of(mustang));
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .get("/api/cars/" + mustang.getCarId())
                .then()
                .body("maker", equalTo(mustang.getMaker()))
                .and().body("model", equalTo(mustang.getModel()))
        ;
        verify(service, times(1)).getCarDetails(mustang.getCarId());
    }
}
