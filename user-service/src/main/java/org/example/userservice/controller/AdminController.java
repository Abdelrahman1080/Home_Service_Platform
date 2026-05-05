package org.example.userservice.controller;


import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import org.example.userservice.dto.LoginRequest;
import org.example.userservice.dto.RegisterRequest;
import org.example.userservice.ejb.AdminBean;
import org.example.userservice.ejb.UserBean;
import org.example.userservice.entity.User;
import org.example.userservice.util.AuthUtil;
import org.example.userservice.util.ResponseUtil;
import org.example.userservice.util.SessionStore;

import java.util.HashMap;
import java.util.Map;

@Path("/admin")
@Produces(MediaType.APPLICATION_JSON)
public class AdminController {

    @Inject
    private AdminBean adminBean;

    @Inject
    private AuthUtil authUtil;

    @Inject
    private UserBean userBean;

    @POST
    @Path("/register")
    public Object register(RegisterRequest req) {
        User user = userBean.register(req.username, req.password, "ADMIN", null);
        return ResponseUtil.success("registered", user);
    }

    @POST
    @Path("/login")
    public Object login(LoginRequest req) {

        User user = userBean.login(req.username, req.password);

        if (user == null)
            return ResponseUtil.error("invalid credentials");

        String sessionId = SessionStore.createSession(user);

        Map<String, Object> data = new HashMap<>();
        data.put("sessionId", sessionId);
        data.put("role", user.getRole().name());
        data.put("userId", user.getId());

        return ResponseUtil.success("login success", data);
    }

    @GET
    @Path("/users")
    public Object all(@Context HttpHeaders headers) {

        User user = authUtil.getUser(headers);

        if (user == null)
            return ResponseUtil.error("invalid session");

        if (!authUtil.isAdmin(user))
            return ResponseUtil.error("only admin allowed");

        return ResponseUtil.success("all users", adminBean.getAllUsers());
    }

    @POST
    @Path("/logout")
    public Object logout(@Context HttpHeaders headers) {

        String sessionId = headers.getHeaderString("X-SESSION-ID");

        if (sessionId == null)
            return ResponseUtil.error("no session found");

        SessionStore.remove(sessionId);

        return ResponseUtil.success("logged out successfully", null);
    }
}