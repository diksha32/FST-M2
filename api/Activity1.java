package Activities;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Activity1 {

    String baseURI= "https://petstore.swagger.io/v2/pet";
    String reqBody= "{"+
            "  \"id\": 77232,\n" +
            "  \"name\": \"Riley\",\n" +
            "  \"status\": \"alive\"\n" +
            "}";
    @Test(priority = 1)
    public void postRequest(){

        Response response= given().contentType(ContentType.JSON).body(reqBody).when().post(baseURI);

        response.then().body("id",equalTo(77232));
        response.then().body("name",equalTo("Riley"));
        response.then().body("status",equalTo("alive"));
    }

    @Test(priority = 2)
    public void getRequest(){

        Response response=given().contentType(ContentType.JSON).when().pathParam("petId","77232").
                get(baseURI+"/{petId}");

        // Assertion
        response.then().body("id", equalTo(77232));
        response.then().body("name", equalTo("Riley"));
        response.then().body("status", equalTo("alive"));
    }

    @Test(priority=3)
    public void deleteRequest(){

        Response response=given().contentType(ContentType.JSON).when().pathParam("petId","77232").
                delete(baseURI+"/{petId}");

        // Assertion
        response.then().body("code", equalTo(200));
        response.then().body("message", equalTo("77232"));
    }


}
