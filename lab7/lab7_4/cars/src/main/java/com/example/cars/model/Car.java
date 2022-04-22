package com.example.cars.model;

import com.example.cars.dto.CarDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cars")
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "carId")
    private long carId;

    @Column(name = "maker")
    private String maker;

    @Column(name = "model")
    private String model;

    public Car(String maker, String model) {
        this.maker = maker;
        this.model = model;
    }

    public Car(CarDto carDto) {
        this(carDto.getMaker(), carDto.getModel());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return carId == car.carId && Objects.equals(maker, car.maker) && Objects.equals(model, car.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carId, maker, model);
    }
}
