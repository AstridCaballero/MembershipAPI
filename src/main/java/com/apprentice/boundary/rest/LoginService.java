package com.apprentice.boundary.rest;

import com.apprentice.server.LoginBean;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

@Path("/login")
public class LoginService {

    private Set<LoginBean> LoginIn = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

    public LoginService() {
        LoginIn.add(new LoginBean("r7jTG7dqBy5wGO4L", "1234"));
        LoginIn.add(new LoginBean("r7jTG7dqBy5wGO4K", "4678"));
    }

    @GET
    public Set<LoginBean> list() {
        return LoginIn;
    }

    @POST
    public Set<LoginBean> add(LoginBean fruit) {
        LoginIn.add(fruit);
        return LoginIn;
    }

}
