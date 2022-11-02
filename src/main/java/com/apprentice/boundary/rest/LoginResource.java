package com.apprentice.boundary.rest;

import com.apprentice.models.Card;
import com.apprentice.models.Employee;
import com.apprentice.service.CardService;
import com.apprentice.service.EmployeeService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//@ApplicationScoped allows Quarkus to recognise this interface and inject it when called
@ApplicationScoped
@Tag(name = "Service to login user")
@Path("/login")
public class LoginResource {
    @Inject
    CardService cardService;

    @Inject
    EmployeeService employeeService;

    /**
     * Gets the name of the Employee if card is registered
     * @param cardId
     * @return String
     */
    @GET
    @Path("/{cardId}")
    @Operation(summary = "Checks if user if registered")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    @APIResponse(responseCode = "200", description = "User is registered")
    @APIResponse(responseCode = "404", description = "User is not found")
    @APIResponse(responseCode = "500", description = "Internal Server Error")

    public Response employeeName(@PathParam("cardId") final String cardId) {
        Card card = cardService.findCard(cardId);
        if (card != null) {
            Employee employee = card.getEmployee();
            return Response.ok(employee.getEmployeeName()).build();
        }
        return Response.ok("Card is not registered").build();
    }

    /**
     * This POst request takes the Employee information, creates a record in Employee table and a record in Card table
     * @param card
     * @return card in Json format
     */
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerCard(@RequestBody Card card) {
//      First persist the child class
        employeeService.registerEmployee(card.getEmployee());
//      Then persist the parent class
        cardService.registerCard(card);
        return Response.ok(card).status(Response.Status.CREATED).build();
    }
}
