package com.apprentice.boundary.rest;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.*;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.StringContains.containsString;

@QuarkusTest
@Tag("Integration")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WelcomeScreenResourceTestIT {
    @Inject
    WelcomeScreenResource welcomeScreenResource;

    /**
     * Test for the POST request to top Up a card and create a record of the transaction
     */
    @Test
    @Order(5)
    @DisplayName("Creates a Card and an Employee record in each table")
    public void postCardAndEmployeeTest() {
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
            .post("http://localhost:8100/api/r7jTG7dqBy5wGO4L/welcome/topUp")
            .then()
            .body(containsString("\"topUpAmount\":7.0"))
            .statusCode(Response.Status.CREATED.getStatusCode());
    }

    /**
     * Test for the POST request to create an OrderEmployee and its associations
     */
    @Test
    @Order(6)
    @DisplayName("Creates an OrderEmployee and its associations")
    public void postCreateOrderTest() {
        // The below Json structure to create the Card and Employee during registration,
        // is written as a JsonObject.
        //        """
        //            {
        //              "orderEmployeeId": 0,
        //              "card": {
        //                "cardId": "string",
        //                "employee": {
        //                  "cardId": "string",
        //                  "employeeId": "string",
        //                  "employeeName": "string",
        //                  "employeeEmail": "string",
        //                  "employeeMobileNumber": "string"
        //                },
        //                "cardPassCode": 0
        //              },
        //              "orderTotal": 0
        //            }
        //            """

        // The whole mapping of the associated entities is not needed
        // So I will use only the add fields to the JsonObject
        // to create a new OrderEmployee
        JsonObject jsonObject =
            Json.createObjectBuilder()
                .add("card", Json.createObjectBuilder()
                    .add("cardId", "r7jTG7dqBy5wGO4L")
                    .add("employee", Json.createObjectBuilder()
                        .add("cardId", "r7jTG7dqBy5wGO4L")))
                .build();

        //Using RestAssured
        given()
            .contentType(MediaType.APPLICATION_JSON)
            .body(jsonObject.toString())
            .when()
            .post("http://localhost:8100/api/r7jTG7dqBy5wGO4L/welcome/order/createOrder")
            .then()
            .body("orderEmployeeId", equalTo(3))
            .statusCode(Response.Status.CREATED.getStatusCode());
    }

    /**
     * Test for the POST request to create an OrderProduct and its associations
     */

    @Test
    @Order(7)
    @DisplayName("Creates an OrderProduct and its associations")
    public void postCreateOrderProductTest() {
        // The below Json structure to create the Card and Employee during registration,
        // is written as a JsonObject.
        //                """
        //                    {
        //                      "orderProductsId": 0,
        //                      "product": {
        //                        "productId": 0,
        //                        "productName": "string",
        //                        "productPrice": 0
        //                      },
        //                      "orderEmployee": {
        //                        "orderEmployeeId": 0,
        //                        "card": {
        //                          "cardId": "string",
        //                          "employee": {
        //                            "cardId": "string",
        //                            "employeeId": "string",
        //                            "employeeName": "string",
        //                            "employeeEmail": "string",
        //                            "employeeMobileNumber": "string"
        //                          },
        //                          "cardPassCode": 0
        //                        },
        //                        "orderTotal": 0
        //                      },
        //                      "orderProductsPrice": 0
        //                    }
        //                    """


        // The whole mapping of the associated entities is not needed
        // So I will use only the add fields to the JsonObject
        // to create a new OrderProducts
        JsonObject jsonObject =
            Json.createObjectBuilder()
                .add("product", Json.createObjectBuilder()
                    .add("productId", 1))
                .add("orderEmployee", Json.createObjectBuilder()
                    .add("orderEmployeeId", 1))
                .build();

        //Using RestAssured
        given()
            .contentType(MediaType.APPLICATION_JSON)
            .body(jsonObject.toString())
            .when()
            .post("http://localhost:8100/api/r7jTG7dqBy5wGO4L/welcome/order/createOrderProduct")
            .then()
            .body("orderProductsId", equalTo(3))
            .statusCode(Response.Status.CREATED.getStatusCode());
    }

    /**
     * Test for the Get all products request
     */
    @Test
    @Order(8)
    @DisplayName("Checks that it gets all the products")
    public void getAllProductsTest() {
        //Using RestAssured
        given()
            .when()
            .get("http://localhost:8100/api/r7jTG7dqBy5wGO4L/welcome/order/allProducts")
            .then()
            .body(containsString("Tuna sandwich"))
            .body(containsString("Orange juice"))
            .statusCode(Response.Status.OK.getStatusCode());
    }

    /**
     * Test for the Delete request of an OrderProducts
     */
    @Test
    @Order(9)
    @DisplayName("Checks an OrderProducts has been deleted")
    public void deleteOrderProductTest() {
        //Using RestAssured
        given()
            .when()
            .delete("http://localhost:8100/api/r7jTG7dqBy5wGO4L/welcome/order/1/1")
            .then()
            .statusCode(Response.Status.NO_CONTENT.getStatusCode());
    }

    /**
     * Test for the Put request to update OrderProduct quantity and price
     */
    @Test
    @Order(10)
    @DisplayName("Checks that OrderProduct quantity and price gets updated when increases in one unit")
    public void putOrderProductUpdateTest() {
        //Using RestAssured
        given()
            .when()
            .put("http://localhost:8100/api/r7jTG7dqBy5wGO4L/welcome/order/1/2")
            .then()
            .body(containsString("OrderProduct updated in order"));
    }

    /**
     * Series of Tests for the Put request to update Card's balance when an order is placed for payment
     */
    @Test
    @Order(11)
    @DisplayName("Checks that the balance of a Card gets updated when an order is placed for payment and is successful")
    public void putOrderPaymentTest() {
        //Using RestAssured
        given()
            .when()
            .put("http://localhost:8100/api/r7jTG7dqBy5wGO4L/welcome/order/1/payment")
            .then()
            .body(containsString("Payment successful"))
            .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    @Order(12)
    @DisplayName("Checks that the balance of a Card gets updated when an order is placed for payment and card has insufficient funds")
    public void putOrderBiggerThanBalancePaymentTest() {
        //Using RestAssured
        given()
            .when()
            .put("http://localhost:8100/api/r7jTG7dqBy5wGO5k/welcome/order/2/payment")
            .then()
            .body(containsString("Insufficient funds"))
            .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

}