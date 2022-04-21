package com.example.cars;

import com.example.cars.model.Car;
import com.example.cars.repository.CarRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @AutoConfigureTestDatabase
@TestPropertySource(locations = "application-integrationtest.properties")
public class CarControllerIT {
    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CarRepository repository;

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }

    @Test
    void whenValidInput_thenCreateCar() {
        Car mustang = new Car("Ford", "Mustang");
        ResponseEntity<Car> entity = restTemplate.postForEntity("/api/cars", mustang, Car.class);
        List<Car> found = repository.findAll();
        Assertions.assertThat(found).extracting(Car::getModel).containsOnly(mustang.getModel());
    }

    @Test
    void givenCars_whenGetCars_thenStatus200() {
        createTestCar("Ford", "Mustang");
        createTestCar("Chevrolet", "Corvette");
        ResponseEntity<List<Car>> response = restTemplate.exchange(
                "/api/cars", HttpMethod.GET, null, new ParameterizedTypeReference<List<Car>>() {});
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).extracting(Car::getModel).containsOnly("Mustang", "Corvette");
    }

    private void createTestCar(String maker, String model) {
        repository.saveAndFlush(new Car(maker, model));
    }
}
