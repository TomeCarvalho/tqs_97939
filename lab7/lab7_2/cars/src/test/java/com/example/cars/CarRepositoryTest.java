package com.example.cars;

import com.example.cars.model.Car;
import com.example.cars.repository.CarRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest
public class CarRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarRepository carRepository;

    @Test
    void whenFindCarById_thenReturnCar() {
        Car mustang = new Car("Ford", "Mustang");
        entityManager.persistAndFlush(mustang);
        Car found = carRepository.findByCarId(mustang.getCarId());
        assertThat(found, equalTo(mustang));
    }

    @Test
    void whenInvalidCarId_thenReturnNull() {
        assertThat(carRepository.findByCarId(-1L), nullValue());
    }

    @Test
    void givenSetOfCars_whenFindAll_thenReturnAllCars() {
        Car mustang = new Car("Ford", "Mustang");
        Car corvette = new Car("Chevrolet", "Corvette");
        Car accord = new Car("Honda", "Accord");
        entityManager.persist(mustang);
        entityManager.persist(corvette);
        entityManager.persist(accord);
        entityManager.flush();
        List<Car> cars = carRepository.findAll();
        Assertions.assertThat(cars).hasSize(3).extracting(Car::getModel).containsOnly(mustang.getModel(), corvette.getModel(), accord.getModel());
    }
}
