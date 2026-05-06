package org.example.userservice.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.userservice.dto.AuthResponse;
import org.example.userservice.entity.User;
import org.example.userservice.util.AuthUtil;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthController {

    @Inject
    private AuthUtil authUtil;

    @Context
    private HttpHeaders headers;

    @GET
    @Path("/validate")
    public Response validate() {
        User user = authUtil.getUser(headers);

        if (user == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Invalid session")
                    .build();
        }

        return Response.ok(new AuthResponse(
                user.getId(),
                user.getUsername(),
                user.getRole().name()
        )).build();
    }
}
