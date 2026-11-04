package org.openindustries.construction.valuation;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class ValuationResourceTest {

    @Test
    void consumptionDrawsDown() {
        String id = given().contentType("application/json")
                .body("{\"projectId\":\"11111111-1111-1111-1111-111111111111\",\"contractValueMinor\":9000000}")
                .when().post("/api/v1/valuations")
                .then().statusCode(201).body("contractValueMinor", is(9000000))
                .extract().path("id");

        given().contentType("application/json")
                .body("{\"workType\":\"STRUCTURE\",\"amountMinor\":1500000}")
                .when().post("/api/v1/valuations/" + id + "/certificates")
                .then().statusCode(200)
                .body("remainingAfter", is(7500000));
    }

    @Test
    void overdrawIsRejected() {
        String id = given().contentType("application/json")
                .body("{\"projectId\":\"11111111-1111-1111-1111-111111111111\",\"contractValueMinor\":9000000}")
                .when().post("/api/v1/valuations")
                .then().statusCode(201)
                .extract().path("id");

        given().contentType("application/json")
                .body("{\"workType\":\"STRUCTURE\",\"amountMinor\":99000000}")
                .when().post("/api/v1/valuations/" + id + "/certificates")
                .then().statusCode(422).body("status", is(422));

        given().when().get("/api/v1/valuations/" + id)
                .then().statusCode(200).body("contractValueMinor", is(9000000));
    }
}
