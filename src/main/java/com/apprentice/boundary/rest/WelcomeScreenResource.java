package com.apprentice.boundary.rest;

import com.apprentice.models.Card;
import com.apprentice.models.OrderEmployee;
import com.apprentice.models.OrderProducts;
import com.apprentice.models.TopUp;
import com.apprentice.service.*;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static io.undertow.httpcore.StatusCodes.INTERNAL_SERVER_ERROR;
import static io.undertow.httpcore.StatusCodes.NO_CONTENT;

@ApplicationScoped
@Tag(name = "Welcome screen")
@Path("{cardId}/welcome")
public class WelcomeScreenResource {
    /**
     * Create a LOGGER to Log information
     */
    private static final Logger LOGGER = Logger.getLogger(WelcomeScreenResource.class);

    /**
     * Inject the services
     */
    @Inject
    CardService cardService;

    @Inject
    TopUpService topUpService;

    @Inject
    OrderEmployeeService orderEmployeeService;

    @Inject
    OrderProductService orderProductService;

    @Inject
    ProductService productService;

    /**
     * POST request takes amount to top up card, creates a record in topUp table and updates balance in Card table
     * @param topUp
     * @return topUp in Json format
     */
    @POST
    @Path("/topUp")
    @Operation(summary = "Top ups the card")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "201", description = "Top up successful",
    content = {
        @Content(mediaType = "application/json", example = """
            {
              "card": {
                "cardId": "r7jTG7dqBy5wGO4L",
                "employee": null,
                "cardPassCode": 0
              },
              "topUpAmount": 20
            }
            """)
    })
    @APIResponse(responseCode = "500", description = "Internal Server Error")
    public Response topUpCard(
        @RequestBody(description = """
            Input example:
            {
              "card": {
                "cardId": "r7jTG7dqBy5wGO4L"
              },
              "topUpAmount": 20
            }""")
        final TopUp topUp) {
        // The topUp object includes a card attribute. So, I fetched the card attribute by cardId and store it
        final String cardId = topUp.getCard().getCardId();
        final Card card = cardService.findCard(cardId);

        // Get the current balance from Card and the topUp amount then calculate the new balance
        double currentBalance = card.getCardBalance();
        double newBalance = topUp.getTopUpAmount() + currentBalance;
        LOGGER.info("New balance: " + newBalance);

        cardService.updateBalance(cardId, newBalance);
        LOGGER.info("TopUp updated");

        topUpService.registerTopUp(topUp);
        LOGGER.info("TopUp persisted");

        return Response.ok(topUp).status(Response.Status.CREATED).build();
    }

    /**
     * POST request creates a record in OrderEmployee table and links it to the Card table
     * @param orderEmployee
     * @return OrderEmployee in Json format
     */
    @POST
    @Path("/order/createOrder")
    @Operation(summary = "Registers the card and stores Employee details")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "201", description = "Order created",
        content = {
            @Content(mediaType = "application/json", example = """
                {
                  "orderEmployeeId": 1,
                  "card": {
                    "cardId": "r7jTG7dqBy5wGO4L",
                    "employee": {
                      "cardId": "r7jTG7dqBy5wGO4L"
                    },
                    "cardPassCode": 0
                  },
                  "orderTotal": 0
                }""")
        })
    @APIResponse(responseCode = "500", description = "Internal Server Error")
    public Response createOrder(
        @RequestBody(description = """
            Not all the fields are required, 
            so use this JSON example input to create an empty order:
            { \s
              "card": {
                "cardId": "r7jTG7dqBy5wGO4L",
                "employee": {
                  "cardId": "r7jTG7dqBy5wGO4L"     \s
                }
              }
            }""")
        final OrderEmployee orderEmployee) {
        // The orderEmployee object includes a card attribute.
        orderEmployeeService.createEmptyOrder(orderEmployee);
        LOGGER.info("OrderEmployee persisted");
        return Response.ok(orderEmployee).status(Response.Status.CREATED).build();
    }

    /**
     * POST request to create an OrderProducts record and updates the OrderEmployee price
     * Each OrderProduct is adding up to the Order
     * @param orderProducts
     * @return OrderProducts in Json format
     */
    @POST
    @Path("/order/createOrderProduct")
    @Operation(summary = "Creates an OrderProduct to track selected products")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "201", description = "OrderProduct created",
    content = {
        @Content(mediaType = "application/json", example = """
            {
              "orderProductsId": 1,
              "product": {
                "productId": 1,
                "productName": null,
                "productPrice": 0
              },
              "orderEmployee": {
                "orderEmployeeId": 1,
                "card": null,
                "orderTotal": 0
              },
              "orderProductsPrice": 2
            }
            """)
    })
    @APIResponse(responseCode = "500", description = "Internal Server Error")
    public Response createOrder(
        @RequestBody(description = """
            Input example:
            { 
              "product": {
                "productId": 1
              },
              "orderEmployee": {
                "orderEmployeeId": 1                
              }
            }
            """)
        final OrderProducts orderProducts) {
        // When a product is selected, then an OrderProducts record is created with
        // default quantity of One and its respective price
        Long orderProductsId = orderProductService.storeOrderProduct(orderProducts);
        LOGGER.info("orderProducts persisted");

        //Update OrderEmployee
        //Get the persisted orderProducts
        OrderProducts orderProductsPersisted = orderProductService.findByOrderProductId(orderProductsId);
        //find the OrderEmployee
        Long orderEmployeeId = orderProductsPersisted.getOrderEmployee().getOrderEmployeeId();
        //Update OrderEmployee total
        orderEmployeeService.updateOrderEmployee(orderEmployeeId, orderProductsPersisted.getOrderProductsPrice());
        LOGGER.info("OrderEmployee total updated");
        return Response.ok(orderProducts).status(Response.Status.CREATED).build();
    }

    /**
     * GET request returns all the products from DB
     * @return a set of Products in Json format
     */
    @GET
    @Path("/order/allProducts")
    @Operation(summary = "Retrieves all the products")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", description = "Products Found",
        content = {
            @Content(mediaType = "application/json", example = """
                [
                  {
                    "productId": 1,
                    "productName": "Tuna sandwich",
                    "productPrice": 2
                  },
                  {
                    "productId": 2,
                    "productName": "Orange juice",
                    "productPrice": 1
                  }
                ]""")
        })
    @APIResponse(responseCode = "500", description = "Internal Server Error")
    public Response findAllProducts() {
        return Response.ok(productService.findAll()).build();
    }

    /**
     * DELETE request to remove a product ('OrderProducts') from the order ('OrderEmployee')
     * and updates the total price to pay ('orderTotal')
     * @param orderEmployeeId
     * @param orderProductsId
     * @return String
     */
    @DELETE
    @Path("/order/{orderEmployeeId}/{orderProductsId}")
    @Operation(summary = "Deletes orderProduct and updates Order total")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    @APIResponse(responseCode = "204", description = "No Content, OrderProduct removed")
    @APIResponse(responseCode = "500", description = "Internal Server Error")
    public Response deleteOrderProducts(
        @Parameter(description = "Input example: 1")
        @PathParam("orderEmployeeId") final Long orderEmployeeId,
        @Parameter(description = "Input example: 1")
        @PathParam("orderProductsId") final Long orderProductsId) {
        //Get the persisted orderProducts
        OrderProducts orderProductsPersisted = orderProductService.findByOrderProductId(orderProductsId);
        if (orderProductsPersisted != null) {
            //get price and turn negative it, so it will be subtracted from the orderEmployeeTotal
            double priceToSubtract = orderProductsPersisted.getOrderProductsPrice() * -1;

            //Remove OrderProduct
            boolean orderProductDeleted = orderProductService.removeOrderProduct(orderProductsId);
            LOGGER.info("orderProducts deleted");

            //Update OrderEmployee total
            orderEmployeeService.updateOrderEmployee(orderEmployeeId, priceToSubtract);
            LOGGER.info("OrderEmployee total updated");

            if (orderProductDeleted) {
                return Response.status(NO_CONTENT).build();
            } else {
                return Response.status(INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return Response.ok("OrderProduct doesn't exists").status(NO_CONTENT).build();

        }
    }

    /**
     * PUT request to update OrderProduct and OrderEmployee if
     * employee increases the quantity of an exiting OrderProduct
     * @param orderEmployeeId
     * @param orderProductsId
     * @return String
     */
    @PUT
    @Path("/order/{orderEmployeeId}/{orderProductsId}")
    @Operation(summary = "Updates orderProduct quantity and price when another item of the same product is added. It updates the order total.")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    @APIResponse(responseCode = "200", description = "OrderProduct updated",
        content = {
            @Content(mediaType = "text/plain", example = "OrderProduct updated in order")
         })
    @APIResponse(responseCode = "500", description = "Internal Server Error")
    public Response updateOrderProducts(
        @Parameter(description = "Input example: 1")
        @PathParam("orderEmployeeId") final Long orderEmployeeId,
        @Parameter(description = "Input example: 1")
        @PathParam("orderProductsId") final Long orderProductsId) {
        //update the persisted orderProducts
        orderProductService.updateOrderProduct(orderProductsId);
        LOGGER.info("orderProducts updated");

        //Update OrderEmployee
        OrderProducts orderProducts = orderProductService.findByOrderProductId(orderProductsId);
        orderEmployeeService.updateOrderEmployee(orderEmployeeId, orderProducts.getProduct().getProductPrice());
        LOGGER.info("orderEmployee total updated");

        return Response.ok("OrderProduct updated in order").build();
    }

    /**
     * This request will be a POST if there was a table to store the transaction
     * so for the prototype scope I only want to update the Card's balance
     * PUT request to update 'cardBalance' from Card
     * @param cardId
     * @param orderEmployeeId
     * @return String
     */
    @PUT
    @Path("/order/{orderEmployeeId}/payment")
    @Operation(summary = "Takes care of the payment and updates the card's balance if successful")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @APIResponse(responseCode = "200", description = "Payment successful")
    @APIResponse(responseCode = "400", description = "Insufficient funds - Bad request")
    @APIResponse(responseCode = "500", description = "Internal Server Error")
    public Response orderPayment(
        @Parameter(description = "Input example: r7jTG7dqBy5wGO4L")
        @PathParam("cardId") final String cardId,
        @Parameter(description = "Input example: 1")
        @PathParam("orderEmployeeId") final Long orderEmployeeId) {
        //Get orderTotal.
        double orderTotal = orderEmployeeService.findById(orderEmployeeId).getOrderTotal();

        //check if card has balance > than the orderTotal
        if (cardService.isBalanceGreaterThanPayment(cardId, orderTotal)) {
                //update Card balance. orderTotal turned negative to enable subtraction
            cardService.updateBalance(cardId, orderTotal * -1);
            LOGGER.info("Card balance updated");
            return Response.ok("Payment successful").build();
        } else {
            return Response.ok("Insufficient funds, please top up your card").status(Response.Status.BAD_REQUEST).build();
        }
    }
}
