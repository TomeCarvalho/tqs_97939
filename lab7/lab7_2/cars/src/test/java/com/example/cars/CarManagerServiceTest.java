package com.example.cars;

import com.example.cars.model.Car;
import com.example.cars.repository.CarRepository;
import com.example.cars.service.CarManagerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CarManagerServiceTest {
    @Mock(lenient = true)
    private CarRepository carRepository;

    @InjectMocks
    private CarManagerService service;

    private Car mustang;
    private Car corvette;
    private Car accord;
    private List<Car> cars;


    @BeforeEach
    public void setUp() {
        mustang = new Car("Ford", "Mustang");
        mustang.setCarId(111L);
        corvette = new Car("Chevrolet", "Corvette");
        accord = new Car("Honda", "Accord");

        cars = Arrays.asList(mustang, corvette, accord);

        Mockito.when(carRepository.findByCarId(111L)).thenReturn(mustang);
        Mockito.when(carRepository.findAll()).thenReturn(cars);
    }

    @Test
    void whenValidId_thenCarShouldBeFound() {
        Optional<Car> fromDb = service.getCarDetails(111L);
        assertThat(fromDb.isPresent(), is(true));
        Car car = fromDb.get();
        assertThat(car, is(mustang));
    }

    @Test
    void whenInvalidId_thenCarShouldNotBeFound() {
        Optional<Car> fromDb = service.getCarDetails(-99L);
        verifyFindByCarIdIsCalledOnce();
        assertThat(fromDb.isPresent(), is(false));
    }

    @Test
    void testGetAllCars() {
        assertThat(service.getAllCars(), is(cars));
        verify(carRepository, times(1)).findAll();
    }

    private void verifyFindByCarIdIsCalledOnce() {
        verify(carRepository, VerificationModeFactory.times(1)).findByCarId(Mockito.anyLong());
    }
}
