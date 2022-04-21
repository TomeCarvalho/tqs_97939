package n97939.lab7_3;

import lombok.extern.slf4j.Slf4j;
import n97939.lab7_3.data.Employee;
import n97939.lab7_3.data.EmployeeRepository;
import n97939.lab7_3.util.JsonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Lab73Application.class)
@TestPropertySource(properties = "spring.jpa.hibernate.ddl-auto=create")
@AutoConfigureMockMvc
@Slf4j
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

    @Autowired
    private MockMvc mockMvc;

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
        log.info("here");
        System.out.println(repository.findAll().size());
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
        Employee pam = new Employee("Pam Beesly", "pam@dundermifflin.com");
        mockMvc.perform(post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.toJson(pam)))
                .andExpect(status().isCreated());
    }
}
