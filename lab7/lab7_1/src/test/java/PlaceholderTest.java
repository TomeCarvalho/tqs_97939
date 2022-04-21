import org.junit.Test;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

public class PlaceholderTest {
    private static final String URL = "https://jsonplaceholder.typicode.com";

    @Test
    public void todosEndpointAvailable() {
        when().get( URL + "/todos").then().statusCode(200);
    }

    @Test
    public void todos4HasTitleEtPorroTempora() {
        when().get(URL + "/todos/4").then().body("title", equalTo("et porro tempora"));
    }

    @Test
    public void todosIncludeIds198And199() {
        when().get(URL + "/todos").then().body("id", hasItems(198, 199));
    }
}
