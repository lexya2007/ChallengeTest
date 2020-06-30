package API;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class TestAPI {
    @BeforeClass
    public void setUpRestAssured() {
        RestAssured.baseURI = "https://my-json-server.typicode.com";
    }

    @Test
    public void Test_API() {
        given()
                .get("/typicode/demo/posts/1")
                .then().log().body()
                .statusCode(200)
                .assertThat()
                .body("id", equalTo(1))
                .body("title", equalTo("Post 1"));
    }
}