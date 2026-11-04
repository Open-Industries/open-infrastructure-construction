package org.openindustries.construction.functions.siterisk;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class FunctionTest {

    @Test
    void healthyIsNormal() {
        given().contentType("application/json")
                .body("{\"projectId\":\"p-1\",\"incidentIndex\":2,\"delayIndex\":10,\"weatherIndex\":8}")
                .when().post("/siteRiskScorer")
                .then().statusCode(200).body("decision", is("NORMAL")).body("score", is(0));
    }

    @Test
    void stackedSignalsAreCritical() {
        given().contentType("application/json")
                .body("{\"projectId\":\"p-2\",\"incidentIndex\":77,\"delayIndex\":69,\"weatherIndex\":58}")
                .when().post("/siteRiskScorer")
                .then().statusCode(200).body("decision", is("CRITICAL")).body("score", is(100));
    }
}
