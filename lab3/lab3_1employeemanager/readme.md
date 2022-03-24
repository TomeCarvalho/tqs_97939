## 3.1

#### Review Questions

a)





b)

In B_EmployeeService_UnitTest, the repository is mocked (the *@Mock* annotation is used in line 32 for the EmployeeRepository). 

In the setUp method, the behaviour of having three employees ("john", "bob" and "alex") is simulated.



c)

*@Mock* is a shorthand for Mockito's *Mockito.mock()* method. Mockito annotations must be enabled to use it.

*@MockBean* is used to add mocks to a Spring *ApplicationContext*. Any existing single bean of the same type defined in the context will be replaced by the mock. If none is defined, a new one will be added. When *@MockBean* is used on a field, as well as being registered in the application context, the mock will also be injected into the field.

*@Mock* is appropriate for tests that don't require dependencies from the Spring Boot container, as it's fast and favours the isolation of the tested component. On the other hand, if the test relies on the Spring Boot container and we wish to add or mock one of the container's beans, we should use *@MockBean*.



d)

The purpose of the *application-integrationtest.properties* file is to store the application properties to be used in place of those in *application.properties* when performing integration tests. We must indicate *application-integrationtest.properties* as the test property source on the integration file by annotating the class with the *@TestPropertySource* annotation, indicating it this way: *@TestPropertySource(locations = "application-integrationtest.properties")*.


