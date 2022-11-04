package com.apprentice.boundary.rest;

import com.apprentice.models.Card;
import com.apprentice.models.OrderEmployee;
import com.apprentice.models.OrderProducts;
import com.apprentice.models.TopUp;
import com.apprentice.service.*;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Tag(name = "Welcome screen")
@Path("/welcome")
public class WelcomeScreenResource {
    private static final Logger LOGGER = Logger.getLogger(WelcomeScreenResource.class);

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
     * This Post request takes amount to top up card, creates a record in topUp table and updates balance in Card table
     * @param topUp
     * @return topUp in Json format
     */
    @POST
    @Path("/topUp")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "201", description = "Top up successful")
    @APIResponse(responseCode = "500", description = "Internal Server Error")
    public Response topUpCard(@RequestBody final TopUp topUp) {
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
     * This Post request takes amount to top up card, creates a record in topUp table and updates balance in Card table
     * @param orderEmployee
     * @return orderEmployee in Json format
     */
    @POST
    @Path("/order/createOrder")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "201", description = "Order created")
    @APIResponse(responseCode = "500", description = "Internal Server Error")
    public Response createOrder(@RequestBody final OrderEmployee orderEmployee) {
        // The orderEmployee object includes a card attribute.
        orderEmployeeService.createEmptyOrder(orderEmployee);
        LOGGER.info("OrderEmployee persisted");
        return Response.ok(orderEmployee).status(Response.Status.CREATED).build();
    }

    /**
     * Post request to create an OrderProducts record and updates the OrderEmployee price
     * Each OrderProduct is add up to the Order
     * @param orderProducts
     * @return OrderProducts in Json format
     */
    @POST
    @Path("/order/createOrderProduct")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "201", description = "OrderProduct created")
    @APIResponse(responseCode = "500", description = "Internal Server Error")
    public Response createOrder(@RequestBody final OrderProducts orderProducts) {
        // When a product is selected, then an OrderProducts record is created with
        // default quantity of One and its respective price
        Long orderProductsId = orderProductService.storeOrderProduct(orderProducts);
        LOGGER.info("orderProducts persisted");

        //TODO add if to check that orderProductsId is not null else trhow not found

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
     * This Get request returns all the products from DB
     * @return a set of Products in Json format
     */
    @GET
    @Path("/order/allProducts")
    @Operation(summary = "gets all the products")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", description = "Card is registered")
    @APIResponse(responseCode = "404", description = "Card is not found")
    @APIResponse(responseCode = "500", description = "Internal Server Error")
    public Response findAllProducts() {
        return Response.ok(productService.findAll()).build();
    }

    /**
     * Delete request to remove a product ('OrderProducts') from the order ('OrderEmployee')
     * and updates the total price to pay ('orderTotal')
     * @param orderProductsId
     * @return String
     */
    @DELETE
    @Path("/order/{orderEmployeeId}/{orderProductsId}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteOrderProducts(@PathParam("orderEmployeeId") final Long orderEmployeeId,
                                        @PathParam("orderProductsId") final Long orderProductsId) {
        //Get the persisted orderProducts
        OrderProducts orderProductsPersisted = orderProductService.findByOrderProductId(orderProductsId);

        //TODO add if to check that orderProductsPersisted and orderEmployeeToUpdate are not null else throw not found

        //get price and turn negative it, so it will be subtracted from the orderEmployeeTotal
        double priceToSubtract = orderProductsPersisted.getOrderProductsPrice() * -1;

        //Remove OrderProduct
        orderProductService.removeOrderProduct(orderProductsId);
        LOGGER.info("orderProducts deleted");

        //Update OrderEmployee total
        orderEmployeeService.updateOrderEmployee(orderEmployeeId, priceToSubtract);
        LOGGER.info("OrderEmployee total updated");

        return Response.ok("OrderProduct removed from order").build();
    }

    /**
     * PUT request to update OrderProduct and OrderEmployee if
     * employee increases the quantity of an exiting OrderProduct
     * @param orderEmployeeId
     * @param orderProductsId
     * @return
     */
    @PUT
    @Path("/order/{orderEmployeeId}/{orderProductsId}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateOrderProducts(@PathParam("orderEmployeeId") final Long orderEmployeeId,
                                        @PathParam("orderProductsId") final Long orderProductsId) {
        //update the persisted orderProducts
        orderProductService.updateOrderProduct(orderProductsId);
        LOGGER.info("orderProducts updated");

        //TODO add if statement to verify nulls

        //Update OrderEmployee
        OrderProducts orderProducts = orderProductService.findByOrderProductId(orderProductsId);
        orderEmployeeService.updateOrderEmployee(orderEmployeeId, orderProducts.getProduct().getProductPrice());
        LOGGER.info("orderEmployee total updated");

        return Response.ok("OrderProduct updated in order").build();
    }
}
