package org.openindustries.construction.safety;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class SafetyStandardResourceTest {

    @Test
    void activateThenEvaluateAllowed() {
        String id = given().contentType("application/json")
                .body("{\"name\":\"height-work-standard\",\"activityType\":\"HEIGHT_WORK\",\"maxCrewSize\":12,\"maxIncidentRate\":2,\"priority\":1}")
                .when().post("/api/v1/site-safety-rules")
                .then().statusCode(201).body("status", is("DRAFT"))
                .extract().path("id");

        given().when().post("/api/v1/site-safety-rules/" + id + "/activate")
                .then().statusCode(200).body("status", is("ACTIVE"));

        given().contentType("application/json")
                .body("{\"activityType\":\"HEIGHT_WORK\",\"requiredMaxIncidentRate\":3,\"requiredMaxCrewSize\":10}")
                .when().post("/api/v1/site-safety-rules/evaluate")
                .then().statusCode(200).body("decision", is("ALLOWED"));
    }

    @Test
    void evaluateWithoutRuleIsDenied() {
        given().contentType("application/json")
                .body("{\"activityType\":\"CRANE_LIFT\",\"requiredMaxIncidentRate\":3,\"requiredMaxCrewSize\":10}")
                .when().post("/api/v1/site-safety-rules/evaluate")
                .then().statusCode(200).body("decision", is("DENIED"));
    }
}
