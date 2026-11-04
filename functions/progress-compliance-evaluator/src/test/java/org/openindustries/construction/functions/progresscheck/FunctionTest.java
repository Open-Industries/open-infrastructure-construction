package org.openindustries.construction.functions.progresscheck;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class FunctionTest {

    @Test
    void compliant() {
        given().contentType("application/json")
                .body("{\"projectId\":\"p-1\",\"measuredDefectCount\":1,\"targetDefectCount\":5,\"measuredScheduleAdherencePct\":96.0,\"targetScheduleAdherencePct\":95.0}")
                .when().post("/progressComplianceEvaluator")
                .then().statusCode(200).body("compliant", is(true)).body("findings.size()", is(0));
    }

    @Test
    void breachIsReported() {
        given().contentType("application/json")
                .body("{\"projectId\":\"p-2\",\"measuredDefectCount\":14,\"targetDefectCount\":5,\"measuredScheduleAdherencePct\":96.0,\"targetScheduleAdherencePct\":95.0}")
                .when().post("/progressComplianceEvaluator")
                .then().statusCode(200).body("compliant", is(false)).body("findings.size()", is(1));
    }
}
