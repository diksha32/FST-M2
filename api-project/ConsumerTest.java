package LiveProject;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.Matchers;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

@ExtendWith(PactConsumerTestExt.class)
public class ConsumerTest {

    Map<String,String> headers = new HashMap<>();
    String resourcePath= "/api/users";

    //Create contract
    @Pact(consumer="UserConsumer", provider="UserProvider")
    public RequestResponsePact consumerTest(PactDslWithProvider builder){

        //Set the headerrs
        headers.put("Content-Type","application/json");

        //CReate the request and response body
        DslPart requestResponseBody = new PactDslJsonBody()
                .numberType("id",124)
                .stringType("firstName","Diksha")
                .stringType("lastName","Mittal")
                .stringType("mail","diksha.mittal@ibm.com");

        //Create Contract
        return builder.given("A request to create a user")
                .uponReceiving("A request to create user")
                .method("POST")
                .path(resourcePath)
                .headers(headers)
                .body(requestResponseBody)
                .willRespondWith()
                .status(201)
                .body(requestResponseBody)
                .toPact();
        
    }

    @Test
    @PactTestFor(providerName = "UserProvider", port="8282")
    public void consumerTest(){

        //set baseURI
        String baseURI = "http://localhost:8282"+resourcePath;

        //set the request body
        Map<String, Object> reqBody = new HashMap<>();
        reqBody.put("id",9764);
        reqBody.put("firstName","Diksha");
        reqBody.put("lastName","Mittal");
        reqBody.put("mail","diksha.mittal@ibm.com");

        //Generate response  and assert
        given().headers(headers).body(reqBody).
                when().post(baseURI).
                then().statusCode(201).log().all();

    }

}
