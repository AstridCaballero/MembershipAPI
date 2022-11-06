package com.apprentice.boundary.rest;

import com.apprentice.boundary.rest.profiles.TimeoutMinutesTestProfile;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
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
@TestProfile(TimeoutMinutesTestProfile.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LoginResourceTestIT {
    @Inject
    LoginResource loginResource;

    /**
     * Series of Test for the GET request
      */

    @Test
    @Order(1)
    @DisplayName("For registered card check a employee name and Card's current balance is displayed")
    public void getEmployeeNameAndCardBalanceTest() {
        //Using RestAssured
        given()
            .when()
            .get("http://localhost:8100/api/login/r7jTG7dqBy5wGO4L/1234")
            .then()
            .body(containsString("Welcome Magdalena Leon! your Balance is £ 10.00"))
            .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    @Order(2)
    @DisplayName("For unregistered card get NOT FOUND message")
    public void getMessageCardNotFoundTest() {
        //Using RestAssured
        given()
            .when()
            .get("http://localhost:8100/api/login/r7jTG7dqBy/1234")
            .then()
            .body(containsString("Card is not registered"))
            .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    @Order(3)
    @DisplayName("For registered card that provides wrong four-digit passCode get NOT FOUND message")
    public void getMessageFourDigitNotFoundTest() {
        //Using RestAssured
        given()
            .when()
            .get("http://localhost:8100/api/login/r7jTG7dqBy5wGO4L/1111")
            .then()
            .body(containsString("four-digit code is wrong, try again"))
            .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    /**
     * Test for the POST request
     */

    @Test
    @Order(4)
    @DisplayName("Test passes if creates a Card and an Employee record in each table")
    public void postCardAndEmployeeTest() {
        // The below Json structure to create the Card and Employee during registration,
        // is written as a JsonObject
            //  {
            //    "cardId": "string",
            //    "employee": {
            //    "cardId": "string",
            //        "employeeId": "string",
            //        "employeeName": "string",
            //        "employeeEmail": "string",
            //        "employeeMobileNumber": "string"
            //  },
            //    "cardPassCode": 0
            //  }

        JsonObject jsonObject =
            Json.createObjectBuilder()
                .add("cardId", "testCardId")
                .add("employee", Json.createObjectBuilder()
                    .add("employeeId", "testEmployeeId")
                    .add("employeeName", "testName")
                    .add("employeeEmail", "testEmail@test.com")
                    .add("employeeMobileNumber", "testMobileNumber"))
                .add("cardPassCode", 1111)
                .build();
        //Using RestAssured
        given()
            .contentType(MediaType.APPLICATION_JSON)
            .body(jsonObject.toString())
            .when()
            .post("http://localhost:8100/api/login/create")
            .then()
            .statusCode(Response.Status.CREATED.getStatusCode());
    }

    /**
     * Test the Get request to check inactivity
     */
    @Test
    @Order(5)
    @DisplayName("Passes if timeout") //differenceMinutes >= timeoutMinutes // has been changed to zero
    public void getTimeOutTest() {
        //Using RestAssured
        given()
            .when()
            .get("http://localhost:8100/api/login/r7jTG7dqBy5wGO4L")
            .then()
            .body(containsString("timeout"));
    }

}