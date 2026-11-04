package org.openindustries.construction.project;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class ProjectResourceTest {

    @Test
    void createAndFetch() {
        String id = given().contentType("application/json")
                .body("{\"name\":\"River Crossing Bridge\",\"projectType\":\"BRIDGE\",\"regionCode\":\"REG-NORTH\"}")
                .when().post("/api/v1/projects")
                .then().statusCode(201)
                .body("status", is("PLANNED"))
                .body("projectType", is("BRIDGE"))
                .extract().path("id");

        given().when().get("/api/v1/projects/" + id)
                .then().statusCode(200).body("status", is("PLANNED"));
    }

    @Test
    void transitionWithoutBody() {
        // Lifecycle transitions take no body and must not require a Content-Type (no 415).
        String id = given().contentType("application/json")
                .body("{\"name\":\"River Crossing Bridge\",\"projectType\":\"BRIDGE\",\"regionCode\":\"REG-NORTH\"}")
                .when().post("/api/v1/projects")
                .then().statusCode(201)
                .extract().path("id");

        given().when().post("/api/v1/projects/" + id + "/start")
                .then().statusCode(200).body("status", is("IN_PROGRESS"));
    }
}
