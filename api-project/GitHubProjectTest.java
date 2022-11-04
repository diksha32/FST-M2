package LiveProject;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GitHubProjectTest {

    RequestSpecification requestSpec;
    String sshKey= "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCO3mz9orA0D1Rebm3RRW0iELxyz6OISCuSRGV0yD5MzdpCs7P2sD5MCm/fxmrFJSJi4LbQmRIqe6Uf5zj/YxxzjFwiOgt4cAHM+gs/pAMx21+ci1Ly3bfajN2wTAZNkqwTkEqnge8HSHBKNU187H+fc4FbVOIdJQOZI4oVzH58QiTaTDtUmphhfEIS9vbE4zef2El/j7okDTbzDRPGwWOvvMn9BxCjB8q2MTJKoVJVS7RLJMKVej/lGdJI0t6p4rVEkLh+MdtYXJYUyq71xBiP0kPG+OA0tMIK0dcHwuSIEH7HIMR/XDR2wEbCWUyBvNPHyhcLNDEGmpY8UOglpPHl";
    int id;

    @BeforeClass
    public void setup() {
        //Request Specification
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://api.github.com")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization","token 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\n")
                .build();
    }

    @Test(priority = 1)
    public void postRequestTest(){

        Map<String,Object> reqBody= new HashMap<>();
        reqBody.put("title","TestAPIKey");
        reqBody.put("key","ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCO3mz9orA0D1Rebm3RRW0iELxyz6OISCuSRGV0yD5MzdpCs7P2sD5MCm/fxmrFJSJi4LbQmRIqe6Uf5zj/YxxzjFwiOgt4cAHM+gs/pAMx21+ci1Ly3bfajN2wTAZNkqwTkEqnge8HSHBKNU187H+fc4FbVOIdJQOZI4oVzH58QiTaTDtUmphhfEIS9vbE4zef2El/j7okDTbzDRPGwWOvvMn9BxCjB8q2MTJKoVJVS7RLJMKVej/lGdJI0t6p4rVEkLh+MdtYXJYUyq71xBiP0kPG+OA0tMIK0dcHwuSIEH7HIMR/XDR2wEbCWUyBvNPHyhcLNDEGmpY8UOglpPHl");

        //Generate Request
        Response response = given().spec(requestSpec).body(reqBody).when().post("/user/keys");

        //Extract pet id from response-> to pass it to GET amd DELETE request
        id = response.then().extract().path("id");

        //Assertions
        response.then().statusCode(201);
    }

    @Test(priority = 2)
    public void getRequestTest(){

        //System.out.println(petId);
        Response response= given().spec(requestSpec).pathParam("keyId",id).log().all().
                when().get("user/keys/{keyId}");

        //Assertions
        response.then().statusCode(200);

    }

    @Test(priority = 3)
    public void deleteRequestTest(){

        Response response= given().spec(requestSpec).pathParam("keyId", id).log().all()
                .when().delete("user/keys/{keyId}");

        response.then().statusCode(204);
    }

}
