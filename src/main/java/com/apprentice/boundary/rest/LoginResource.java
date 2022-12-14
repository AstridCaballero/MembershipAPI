package com.apprentice.boundary.rest;

import com.apprentice.models.Card;
import com.apprentice.models.Employee;
import com.apprentice.service.CardService;
import com.apprentice.service.EmployeeService;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.Operation;
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
import java.time.ZonedDateTime;

//@ApplicationScoped allows Quarkus to recognise this interface and inject it when called
@ApplicationScoped
@Tag(name = "Login screen")
@Path("/login")
public class LoginResource {
    private static final Logger LOGGER = Logger.getLogger(LoginResource.class);

    // Inject services
    @Inject
    CardService cardService;

    @Inject
    EmployeeService employeeService;

    // Inject Constant to use for tracking inactivity
    @Inject
    @ConfigProperty(name = "TIMEOUT_MINUTES")
    int timeoutMinutes;

    /**
     * GET request that retrieves the name of the Employee if card is registered
     * @param cardId
     * @param cardPassCode
     * @return String
     */
    @GET
    @Path("/{cardId}/{cardPassCode}")
    @Operation(summary = "Checks if user if registered")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    @APIResponse(responseCode = "200", description = "Card is registered")
    @APIResponse(responseCode = "404", description = "Card is not found")
    @APIResponse(responseCode = "500", description = "Internal Server Error")
    public Response findEmployeeCard(
        @Parameter(description = "input example: r7jTG7dqBy5wGO4L")
        @PathParam("cardId") final String cardId,
        @Parameter(description = "input example: 1234")
        @PathParam("cardPassCode") final int cardPassCode) {
        final Card card = cardService.findCard(cardId);

        // If card is registered then card is not null
        if (card != null) {
        // Check four-digit passCode provided by Employee is associated with the card, if so then return Name and balance
            if (card.getCardPassCode() == cardPassCode) {
                final Employee employee = card.getEmployee();

                //set time of interaction to track inactivity
                cardService.updateLastInteractionDateTime(card.getCardId());

                return Response.ok(String.format("Welcome %s! your Balance is ?? %.2f ", employee.getEmployeeName(), employee.getCard().getCardBalance())).build();
            } else {
                // four-digit passCode provided by Employee is not associated with the card
                return Response.ok("four-digit code is wrong, try again").status(Response.Status.NOT_FOUND).build();
            }
        }
        // If card is registered then card is null
        return Response.ok("Card is not registered").status(Response.Status.NOT_FOUND).build();
    }


    /**
     * POST request takes the Employee information, creates a record in Employee table and a record in Card table
     * @param card
     * @return Card in Json format
     */
    @POST
    @Path("/create")
    @Operation(summary = "Registers the card and stores Employee details")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "201", description = "Card registration successful")
    @APIResponse(responseCode = "500", description = "Internal Server Error")
    public Response registerCard(
        @RequestBody(description = """
             JSON example input:           
            {
              "cardId": "r7jTG7dqBy5wGO6A",
              "employee": {
                "cardId": "r7jTG7dqBy5wGO6A",
                "employeeId": "testId",
                "employeeName": "testName",
                "employeeEmail": "test@test.com",
                "employeeMobileNumber": "00000 000000"
              },
              "cardPassCode": 1111
            }""")
        final Card card) {
        // Card and Employee shared primary key ID so by persisting Card,
        // Employee also gets persisted (Cascade), it uses the 'setEmployee(Employee employee)'
        // method from Card entity
        cardService.registerCard(card);
        LOGGER.info("Employee and Card persisted");
        return Response.ok(card).status(Response.Status.CREATED).build();
    }


    /**
     * The iddle timeout of the system will ideally be managed by an UI
     * but as I don't have that, I am creating an endpoint that will update
     * a front end that will be build later (Assumption)
     */

    /**
     *GET request to update front-end with inactivity
     * @param cardId
     * @return
     */
    @GET
    @Path("/{cardId}")
    @Operation(summary = "Checks time since last interaction and logs out after N minutes of inactivity")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response timeout(
        @Parameter(description = "input example: r7jTG7dqBy5wGO4L")
        @PathParam("cardId")
        final String cardId) {
        Card card = cardService.findCard(cardId);
        ZonedDateTime currentDateTime = ZonedDateTime.now();
        int differenceMinutes = (int) (currentDateTime.toEpochSecond() - card.getLastInteractionDateTime().toEpochSecond())/60;


        //timeout after 5 minutes of inactivity
        if (differenceMinutes >= timeoutMinutes) {
            return Response.ok("timeout").build();
        } else {
            return Response.ok("active").build();
        }
    }
}
