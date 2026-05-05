package org.example.userservice;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/hello-world")
public class HelloResource {
    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hel33lo, World!";
    }

    @POST
    @Produces("text/plain")
    public String hello2() {
        return "Hel33lo, World!";
    }
}