package com.apprentice.boundary.rest;

import com.apprentice.models.Card;
import com.apprentice.models.Employee;
import com.apprentice.service.CardService;
import com.apprentice.service.EmployeeService;
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

//@ApplicationScoped allows Quarkus to recognise this interface and inject it when called
@ApplicationScoped
@Tag(name = "Login screen")
@Path("/login")
public class LoginResource {
    private static final Logger LOGGER = Logger.getLogger(LoginResource.class);
    @Inject
    CardService cardService;

    @Inject
    EmployeeService employeeService;

    /**
     * Retrieves the name of the Employee if card is registered
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
    public Response findEmployeeCard(@PathParam("cardId") final String cardId,
                             @PathParam("cardPassCode") final int cardPassCode) {
        final Card card = cardService.findCard(cardId);
        final Employee employee = card.getEmployee();

        if (card != null) {
            if (card.getEmployee().getCard().getCardPassCode() == cardPassCode) {
                return Response.ok("Welcome " + employee.getEmployeeName() + "!\n" +
                    "Balance: £" + employee.getCard().getCardBalance()).build();
            } else {
                return Response.ok("four-digit code is wrong, try again ").status(Response.Status.NOT_FOUND).build();
            }
        }
        return Response.ok("Card is not registered").status(Response.Status.NOT_FOUND).build();
    }


    /**
     * This Post request takes the Employee information, creates a record in Employee table and a record in Card table
     * @param card
     * @return card in Json format
     */
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "201", description = "Card registration successful")
    @APIResponse(responseCode = "500", description = "Internal Server Error")
    public Response registerCard(@RequestBody final Card card) {
        // Card and Employee shared primary key ID so by persisting Card,
        // Employee also gets persisted (Cascade), it uses the 'setEmployee(Employee employee)'
        // method from Card entity
        cardService.registerCard(card);
        LOGGER.info("Employee and Card persisted");
        return Response.ok(card).status(Response.Status.CREATED).build();
    }
}
