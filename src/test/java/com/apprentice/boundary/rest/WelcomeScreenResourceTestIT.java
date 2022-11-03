package com.apprentice.boundary.rest;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.*;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.StringContains.containsString;

@QuarkusTest
@Tag("Integration")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WelcomeScreenResourceTestIT {
    @Inject
    WelcomeScreenResource welcomeScreenResource;

    /**
     * Test for the POST request
     */

    @Test
    @Order(5)
    @DisplayName("Creates a Card and an Employee record in each table")
    public void postCardAndEmployee() {
        // The below Json structure to create the Card and Employee during registration,
        // is written as a JsonObject.
            //{
            //    "card": {
            //    "cardId": "string",
            //        "employee": {
            //        "cardId": "string",
            //            "employeeId": "string",
            //            "employeeName": "string",
            //            "employeeEmail": "string",
            //            "employeeMobileNumber": "string"
            //    },
            //    "cardPassCode": 0
            //},
            //    "topUpAmount": 0
            //}

        // The whole mapping of the associated entities is not needed
        // So I will use only the add fields to the JsonObject
        // to create a new topUp
        JsonObject jsonObject =
            Json.createObjectBuilder()
                .add("card", Json.createObjectBuilder()
                    .add("cardId", "r7jTG7dqBy5wGO4L"))
                .add("topUpAmount", 7)
                .build();

        //Using RestAssured
        given()
            .contentType(MediaType.APPLICATION_JSON)
            .body(jsonObject.toString())
            .when()
            .post("http://localhost:8100/api/welcome/topUp")
            .then()
            .body(containsString("\"topUpAmount\":7.0"))
            .statusCode(Response.Status.CREATED.getStatusCode());
    }
}