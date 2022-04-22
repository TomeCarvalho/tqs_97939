package com.example.cars;

import com.example.cars.model.Car;
import com.example.cars.repository.CarRepository;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CarsApplication.class)
@TestPropertySource(properties = "spring.jpa.hibernate.ddl-auto=create")
@AutoConfigureMockMvc
class RestAssuredIntegrationTest {
    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer("postgres:12")
            .withUsername("user")
            .withPassword("user")
            .withDatabaseName("cars");

    @LocalServerPort
    int testPort;

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    Car mustang, corvette, accord;

    @Autowired
    private CarRepository repository;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void setUp() {
        RestAssuredMockMvc.mockMvc(mvc);
        mustang = repository.save(new Car("Ford", "Mustang"));
        corvette = repository.save(new Car("Chevrolet", "Corvette"));
        accord = new Car("Honda", "Accord");
    }

    @AfterEach
    public void tearDown() {
        repository.deleteAll();
    }

    @Test
    void whenPostCar_thenCreateCar() throws Exception {
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .body(JsonUtils.toJson(accord))
                .when()
                .post("/api/cars")
                .then()
                .statusCode(201)
                .and().body("maker", equalTo(accord.getMaker()))
                .and().body("model", equalTo(accord.getModel()))
        ;
    }

    @Test
    void givenManyCars_whenGetCars_thenReturnJsonArray() {
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .get("/api/cars")
                .then()
                .statusCode(200)
                .body("", hasSize(2))
                .and().body("[0].maker", equalTo(mustang.getMaker()))
                .and().body("[0].model", equalTo(mustang.getModel()))
                .and().body("[1].maker", equalTo(corvette.getMaker()))
                .and().body("[1].model", equalTo(corvette.getModel()))
        ;
    }

    @Test
    void testGetCarById() {
        RestAssuredMockMvc.given()
                .contentType("application/json")
                .get("/api/cars/" + mustang.getCarId())
                .then()
                .body("maker", equalTo(mustang.getMaker()))
                .and().body("model", equalTo(mustang.getModel()))
        ;
    }
}
