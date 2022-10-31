package com.apprentice.boundary.rest;

import com.apprentice.server.LoginBean;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

@ApplicationScoped
@Tag(name = "Service to login user")
@Path("/login")
public class LoginService {

    private final Set<LoginBean> loginDetails = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

    public LoginService() {
        loginDetails.add(new LoginBean("r7jTG7dqBy5wGO4L", "1234"));
        loginDetails.add(new LoginBean("r7jTG7dqBy5wGO4K", "4678"));
    }

    @GET
    @Operation(summary = "Checks if user if registered")
    @APIResponse(responseCode = "200", description = "User is registered")
    @APIResponse(responseCode = "404", description = "User is not found")
    @APIResponse(responseCode = "500", description = "Internal Server Error")
    public Set<LoginBean> list() {
        return loginDetails;
    }

    @POST
    public Set<LoginBean> add(LoginBean userInfo) {
        loginDetails.add(userInfo);
        return loginDetails;
    }

}
