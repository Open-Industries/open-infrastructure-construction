package org.openindustries.construction.asset;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class ConstructionAssetResourceTest {

    @Test
    void createAndTransition() {
        String id = given().contentType("application/json")
                .body("{\"name\":\"Tower Crane C-1\",\"assetType\":\"CRANE\",\"capacityTonnes\":8}")
                .when().post("/api/v1/construction-assets")
                .then().statusCode(201)
                .body("status", is("AVAILABLE"))
                .body("assetType", is("CRANE"))
                .extract().path("id");

        given().when().post("/api/v1/construction-assets/" + id + "/deploy")
                .then().statusCode(200).body("status", is("DEPLOYED"));
    }
}
