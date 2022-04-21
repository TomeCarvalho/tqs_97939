package n97939.lab7_3;

import n97939.lab7_3.data.Employee;
import n97939.lab7_3.data.EmployeeRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.util.UriComponentsBuilder;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestPropertySource(properties = "spring.jpa.hibernate.ddl-auto=create")
public class IntegrationTest {
    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer("postgres:12")
            .withUsername("user")
            .withPassword("user")
            .withDatabaseName("employees");

    @LocalServerPort
    int testPort;
    Employee jim, creed;

    @Autowired
    private EmployeeRepository repository;

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @BeforeEach
    public void setUpTestEmployees() throws Exception {
        jim = repository.save(new Employee("Jim Halpert", "jim@dundermifflin.com"));
        creed = repository.save(new Employee("Creed Bratton", "creed@dundermifflin.com"));
    }

    @Test
    public void testAddEmployee() throws Exception {
        String endpoint = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("127.0.0.1")
                .port(testPort)
                .pathSegment("api", "employees")
                .build()
                .toUriString();
        repository.save(new Employee("Pam Beesly", "pam@dundermifflin.com"));
        assertThat(repository.findAll().size(), equalTo(3));
    }
}
